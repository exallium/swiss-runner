package com.exallium.swissrunner.app.view.fx

import com.exallium.swissrunner.app.viewcontrollers.ViewController
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import java.util.*

object FXLoader {

    private val FXML_DIRECTORY = "/fxml/"

    fun <P, V: ViewController<P>> load(clazz: Class<V>, presenter: P): Parent {
        val path = clazz.getAnnotation(FXPath::class.java)
        val fxLoader = FXMLLoader()
        fxLoader.resources = ResourceBundle.getBundle("bundles.SwissRunnerBundle")
        val fullPath = "$FXML_DIRECTORY${path.xmlPath}"
        val root = fxLoader.load<Parent>(Any::class.java.getResourceAsStream(fullPath))
        val controller: V = fxLoader.getController<V>()
        controller.presenter = presenter
        return root
    }

}
