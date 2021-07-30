package de.intelligence.bachelorarbeit.demoapplications.realapp.simplifx.controller;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

import de.intelligence.bachelorarbeit.demoapplications.realapp.simplifx.controller.TestControllers.TestControllerOne;
import de.intelligence.bachelorarbeit.demoapplications.realapp.simplifx.controller.TestControllers.TestControllerThree;
import de.intelligence.bachelorarbeit.demoapplications.realapp.simplifx.controller.TestControllers.TestControllerTwo;
import de.intelligence.bachelorarbeit.simplifx.annotation.PostConstruct;
import de.intelligence.bachelorarbeit.simplifx.config.ConfigValue;
import de.intelligence.bachelorarbeit.simplifx.controller.Controller;
import de.intelligence.bachelorarbeit.simplifx.controller.ControllerGroupContext;
import de.intelligence.bachelorarbeit.simplifx.controller.ControllerSetupContext;
import de.intelligence.bachelorarbeit.simplifx.controller.OnHide;
import de.intelligence.bachelorarbeit.simplifx.controller.Setup;
import de.intelligence.bachelorarbeit.simplifx.controller.animation.LeftSlideAnimation;
import de.intelligence.bachelorarbeit.simplifx.controller.animation.RightSlideAnimation;
import de.intelligence.bachelorarbeit.simplifx.controller.animation.TopSlideAnimation;
import de.intelligence.bachelorarbeit.simplifx.localization.LocalizeValue;

@Controller(fxml = "/fxml/SidebarController.fxml")
public final class SidebarController {

    @FXML
    private Label connectionLbl;

    @ConfigValue("host")
    private String hostname;

    @ConfigValue(value = "port")
    private int port;

    @LocalizeValue(id = "connectionLbl", property = "text")
    private final StringProperty hostProperty = new SimpleStringProperty();

    @LocalizeValue(id = "connectionLbl", index = 1, property = "text")
    private final IntegerProperty portProperty = new SimpleIntegerProperty();

    private ControllerGroupContext mainCtx;
    private ControllerGroupContext sidebarContentCtx;

    @Setup //TODO direct ctx injection
    private void onSetup(ControllerSetupContext ctx) {
        this.mainCtx = ctx.getContextFor("mainContent");
        this.sidebarContentCtx = ctx.getContextFor("sidebarContent");
    }

    @PostConstruct
    private void afterConstruction() {
        this.hostProperty.set(this.hostname);
        this.portProperty.set(this.port);
    }

    @OnHide
    private void onHide() {
        this.sidebarContentCtx.switchController(TestControllerOne.class);
    }

    @FXML
    private void onLogoutPressed() {
        this.sidebarContentCtx.getContextFor("main").destroyGroup();
        //this.mainCtx.switchController(LoginController.class, new BottomSlideAnimation(Duration.millis(250)));
    }

    @FXML
    private void onOnePressed() {
        this.sidebarContentCtx.switchController(TestControllerOne.class, new TopSlideAnimation(Duration.millis(250)));
    }

    @FXML
    private void onTwoPressed() {
        this.sidebarContentCtx.switchController(TestControllerTwo.class, new RightSlideAnimation(Duration.millis(250)));
    }

    @FXML
    private void onThreePressed() {
        this.sidebarContentCtx.switchController(TestControllerThree.class, new LeftSlideAnimation(Duration.millis(250)));
    }

}
