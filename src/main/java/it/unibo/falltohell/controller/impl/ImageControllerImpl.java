package it.unibo.falltohell.controller.impl;

import it.unibo.falltohell.controller.api.ImageController;

import java.awt.*;
import java.util.Map;

public class ImageControllerImpl implements ImageController {

    private static final String IMAGE_DIR_PATH = "src/main/resources/sprites/";

    @Override
    public Map<String, Image> createImageMap() {
        //TODO --> change method
        return Map.of();
    }
}
