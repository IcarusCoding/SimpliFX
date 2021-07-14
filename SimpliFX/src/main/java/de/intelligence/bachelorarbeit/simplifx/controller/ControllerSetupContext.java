package de.intelligence.bachelorarbeit.simplifx.controller;

import java.util.function.Consumer;

import javafx.scene.layout.Pane;

//TODO interfaces
public final class ControllerSetupContext {

    private final Class<?> controllerClass;
    private final IControllerGroup group;
    private final ControllerGroupContext groupCtx;

    public ControllerSetupContext(Class<?> controllerClass, IControllerGroup group, ControllerGroupContext groupCtx) {
        this.controllerClass = controllerClass;
        this.group = group;
        this.groupCtx = groupCtx;
    }

    public void createSubGroup(Class<?> clazz, String groupId, Consumer<Pane> readyConsumer) {
        this.group.registerSubGroup(controllerClass, clazz, groupId, readyConsumer);
    }

    public void switchController(Class<?> clazz) {
        groupCtx.switchController(clazz);
    }

    public void switchController(Class<?> clazz, IWrapperAnimationFactory factory) {
        groupCtx.switchController(clazz, factory);
    }

    public ControllerGroupContext getContextFor(String groupId) {
        return groupCtx.getContextFor(groupId);
    }

    public Class<?> getActiveController() {
        return groupCtx.getActiveController();
    }

}