/*
 * The MIT License (MIT)
 * Copyright (c) 2016 Alex Hart
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN
 * AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
