module Gui {
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.controls;
	requires javafx.base;
	requires json.simple;
	exports application;
	opens application;
}