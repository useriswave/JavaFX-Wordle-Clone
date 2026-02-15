module WordleFX {
    requires javafx.graphics;
    requires javafx.controls;
    requires jdk.xml.dom;
    requires java.desktop;
//    requires WordleFX;
    exports org.example.wordlefx.main;
    exports org.example.wordlefx.enums;
    exports org.example.wordlefx.logic;
}