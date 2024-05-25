package co.edu.uptc.Runner;

import java.io.IOException;
import co.edu.uptc.Interfaces.VetInterface;
import co.edu.uptc.VetPresenter.VetPresenter;
import co.edu.uptc.models.VetManager;
import co.edu.uptc.view.MainView.MainView;

public class Main {
    public static void main(String[] args) {
        VetInterface.Model model = new VetManager();
        VetPresenter presenter = new VetPresenter();
        MainView view = null;

        try {
            view = new MainView();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        model.setPresenter(presenter);
        view.setPresenter(presenter);
        presenter.setModel(model);
        presenter.setView(view);

        view.begin();
    }
}
