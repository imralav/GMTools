package com.imralav.gmtools.gui.audiomanager.views;

import com.imralav.gmtools.gui.audiomanager.model.AudioManager;
import com.imralav.gmtools.gui.audiomanager.model.Category;
import com.imralav.gmtools.gui.audiomanager.persistence.CategoryFileReader;
import com.imralav.gmtools.gui.audiomanager.persistence.CategoryFileWriter;
import com.imralav.gmtools.gui.audiomanager.persistence.CustomFileChooser;
import com.imralav.gmtools.gui.audiomanager.players.SingleTrackPlayer;
import com.imralav.gmtools.gui.audiomanager.players.SingleTrackPlayerManager;
import com.imralav.gmtools.gui.utils.CurtainManager;
import com.imralav.gmtools.gui.utils.ViewsLoader;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Slf4j
@Controller
public class AudioManagerView extends BorderPane {
    private static final String VIEW_PATH = "audiomanager/audiomanager.fxml";
    private static final DataFormat CATEGORY_ID_DATAFORMAT = new DataFormat("categoryView");
    private final AudioManager audioManager;

    @FXML
    private TextField categoryNameField;

    @FXML
    private HBox categoriesContainer;

    private SingleTrackPlayerManager singleTrackPlayerManager;
    private CustomFileChooser fileChooser;
    private CategoryFileWriter categoryFileWriter;
    private CategoryFileReader categoryFileReader;
    private Category currentlyPlayingCategory;
    private CurtainManager curtainManager;

    @Autowired
    public AudioManagerView(CurtainManager curtainManager, AudioManager audioManager, ApplicationContext context) throws IOException {
        FXMLLoader fxmlLoader = ViewsLoader.getViewLoader(VIEW_PATH, context);
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
        fileChooser = new CustomFileChooser();
        this.audioManager = audioManager;
        categoryFileWriter = new CategoryFileWriter(audioManager);
        categoryFileReader = new CategoryFileReader(audioManager);
        singleTrackPlayerManager = new SingleTrackPlayerManager();
        this.curtainManager = curtainManager;
        setupCategoriesListener();
        setupCategoriesDragAndDrop();
    }

    private void setupCategoriesDragAndDrop() {
        categoriesContainer.setOnDragOver(event -> {
            if (isCorrectCategoryMoveEvent(event)) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });
        categoriesContainer.setOnDragEntered(event -> {
            if (isCorrectCategoryMoveEvent(event)) {
                categoriesContainer.getStyleClass().add("category-drag-entered");
            }
            event.consume();
        });
        categoriesContainer.setOnDragExited(event -> {
            categoriesContainer.getStyleClass().remove("category-drag-entered");
            event.consume();
        });
        categoriesContainer.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            if (isCorrectCategoryMoveEvent(event)) {
                int categoryId = (Integer) db.getContent(CATEGORY_ID_DATAFORMAT);
                moveCategory(categoriesContainer, categoryId, event);
                event.setDropCompleted(true);
                event.consume();
            }
        });
    }

    private void moveCategory(HBox categoriesContainer, int categoryId, DragEvent event) {
        ObservableList<Node> categories = categoriesContainer.getChildren();
        for (int i = 0; i < categories.size(); i++) {
            Bounds boundsInParent = categories.get(i).getBoundsInParent();
            if (event.getX() <= boundsInParent.getMinX() + boundsInParent.getWidth() / 2) {
                audioManager.moveCategory(categoryId, i);
                return;
            }
        }
        audioManager.moveCategoryToTheEnd(categoryId);
    }

    private boolean isCorrectCategoryMoveEvent(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        return event.getGestureSource() != categoriesContainer && dragboard.hasContent(CATEGORY_ID_DATAFORMAT) && dragboard.getContent(CATEGORY_ID_DATAFORMAT) instanceof Integer;
    }

    private void setupCategoriesListener() {
        audioManager.getCategories().addListener((ListChangeListener<? super Category>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    List<CategoryView> views = change.getAddedSubList().stream()
                            .map(this::createNewCategoryView)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                    Platform.runLater(() -> {
                        categoriesContainer.getChildren().addAll(change.getFrom(), views);
                    });
                }
                if (change.wasRemoved()) {
                    singleTrackPlayerManager.remove(change.getRemoved());
                    Platform.runLater(() -> {
                        categoriesContainer.getChildren().removeIf(categoryView -> {
                            CategoryView view = (CategoryView) categoryView;
                            return change.getRemoved().contains(view.getCategory());
                        });
                    });
                }
            }
        });
    }

    private CategoryView createNewCategoryView(Category category) {
        try {
            SingleTrackPlayer player = singleTrackPlayerManager.getPlayer(category);
            player.setOnPlayingAction(replaceCurrentPlayer(category));
            CategoryView categoryView = new CategoryView(category, player, fileChooser);
            categoryView.setOnRemoveAction(removedCategory -> {
                audioManager.removeCategory(removedCategory);
                player.stopCurrentMusic();
            });
            categoryView.setOnDragDetected(event -> {
                Dragboard db = categoryView.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                int categoryViewId = categoriesContainer.getChildren().indexOf(categoryView);
                content.put(CATEGORY_ID_DATAFORMAT, categoryViewId);
                db.setContent(content);
                event.consume();
                categoryView.getStyleClass().add("dragged");
            });
            categoryView.setOnDragDone(event -> {
                categoryView.getStyleClass().remove("dragged");
                if (event.getTransferMode() == TransferMode.MOVE) {
                    log.info("Finished dragging");
                }
                event.consume();
            });
            return categoryView;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    private Consumer<SingleTrackPlayer> replaceCurrentPlayer(Category category) {
        return newPlayer -> {
            if (nonNull(currentlyPlayingCategory) && currentlyPlayingCategory != category) {
                singleTrackPlayerManager.getPlayer(currentlyPlayingCategory).pauseWithFadeOut();
            }
            currentlyPlayingCategory = category;
        };
    }

    @FXML
    public void addCategory() throws IOException {
        String categoryName = categoryNameField.getText();
        if (isEmpty(categoryName)) {
            return;
        }
        audioManager.addCategory(categoryName);
        categoryNameField.clear();
    }

    @FXML
    public void saveCategories() throws IOException {
        curtainManager.fadeInCurtainFor("Saving categories...");
        File file = fileChooser.openSaveCategoriesDialog(this.getScene().getWindow());
        if (nonNull(file)) {
            processCategoryPersisting(file, processedFile -> {
                try {
                    categoryFileWriter.write(processedFile);
                } catch (IOException e) {
                    log.error("Error occured when writing categories to {}, {}", processedFile.getPath(), e.getMessage(), e);
                }
            });
        } else {
            curtainManager.fadeOutCurrentCurtain();
        }
    }

    private void processCategoryPersisting(File file, Consumer<File> action) {
        Executors.newSingleThreadExecutor().execute(() -> {
            action.accept(file);
            Platform.runLater(() -> {
                curtainManager.fadeOutCurrentCurtain();
            });
        });
    }

    @FXML
    public void openCategories() {
        curtainManager.fadeInCurtainFor("Loading categories...");
        File file = fileChooser.openLoadCategoriesDialog(this.getScene().getWindow());
        if (nonNull(file)) {
            processCategoryPersisting(file, processedFile -> {
                try {
                    categoryFileReader.read(processedFile);
                } catch (IOException e) {
                    log.error("Error occured when reading categories from {}, {}", processedFile.getPath(), e.getMessage(), e);
                }
            });
        } else {
            curtainManager.fadeOutCurrentCurtain();
        }
    }
}
