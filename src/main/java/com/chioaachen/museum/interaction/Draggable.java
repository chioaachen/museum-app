package com.chioaachen.museum.interaction;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public final class Draggable {

  private Draggable() {
  }

  public static void makeDraggable(Node eventNode) {
    makeDraggable(eventNode, eventNode);
  }

  public static void makeDraggable(Node eventNode, Node... nodesToDrag) {
    new Nature(eventNode, nodesToDrag);
  }

  private static final class Nature implements EventHandler<MouseEvent> {

    private double lastMouseX;
    private double lastMouseY;
    private boolean isDragging;

    private final Node eventNode;
    private final List<Node> dragNodes;
    private final List<BiConsumer<Nature, MouseDragEvent>> dragListeners;

    public Nature(final Node node) {
      this(node, node);
    }

    public Nature(final Node eventNode, final Node... nodesToDrag) {
      lastMouseX = 0;
      lastMouseY = 0;
      isDragging = false;
      dragNodes = new ArrayList<>();
      dragListeners = new ArrayList<>();

      dragNodes.addAll(List.of(nodesToDrag));

      this.eventNode = eventNode;
      this.eventNode.addEventHandler(MouseEvent.ANY, this);
    }

    @Override
    public void handle(final MouseEvent event) {
      if (MouseEvent.MOUSE_PRESSED == event.getEventType()) {
        if (eventNode.contains(event.getX(), event.getY())) {
          lastMouseX = event.getSceneX();
          lastMouseY = event.getSceneY();
          event.consume();
        }
      } else if (MouseEvent.MOUSE_DRAGGED == event.getEventType()) {
        if (!isDragging) {
          isDragging = true;
          for (final BiConsumer<Nature, MouseDragEvent> listener : dragListeners) {
            listener.accept(this, MouseDragEvent.DRAG_START);
          }
        }
        if (isDragging) {
          final double deltaX = event.getSceneX() - lastMouseX;
          final double deltaY = event.getSceneY() - lastMouseY;

          for (final Node dragNode : dragNodes) {
            final double initialTranslateX = dragNode.getTranslateX();
            final double initialTranslateY = dragNode.getTranslateY();
            dragNode.setTranslateX(initialTranslateX + deltaX);
            dragNode.setTranslateY(initialTranslateY + deltaY);
          }

          lastMouseX = event.getSceneX();
          lastMouseY = event.getSceneY();

          event.consume();

          for (final BiConsumer<Nature, MouseDragEvent> listener : dragListeners) {
            listener.accept(this, MouseDragEvent.DRAGGING);
          }
        }
      } else if (MouseEvent.MOUSE_RELEASED == event.getEventType()) {
        if (isDragging) {
          event.consume();
          isDragging = false;
          for (final BiConsumer<Nature, MouseDragEvent> listener : dragListeners) {
            listener.accept(this, MouseDragEvent.DRAG_END);
          }
        }
      }
    }
  }
}