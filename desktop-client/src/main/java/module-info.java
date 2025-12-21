module project.task.manager.desktopclient {
		requires javafx.controls;
		requires javafx.fxml;
		requires javafx.web;

		requires org.controlsfx.controls;
		requires com.dlsc.formsfx;
		requires net.synedra.validatorfx;
		requires org.kordamp.ikonli.javafx;
		requires org.kordamp.bootstrapfx.core;
		requires eu.hansolo.tilesfx;
		requires com.almasb.fxgl.all;
		requires kotlin.stdlib;

		opens project.task.manager.desktopclient to javafx.fxml;
		exports project.task.manager.desktopclient;
}