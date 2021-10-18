package co.edu.unbosque.view;

import org.apache.commons.io.FileUtils;
import org.beryx.textio.*;
import org.beryx.textio.web.RunnerData;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.function.BiConsumer;

public class MiscIOUtils implements BiConsumer<TextIO, RunnerData> {
    private File logo;
    private String[] ui;
    private TextIO tio;

    public MiscIOUtils() {
        logo = new File("data\\ui.txt");
        try {
            loadUI();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    public void accept(TextIO textIO, RunnerData runnerData) {
        TextTerminal<?> terminal = textIO.getTextTerminal();

        String command = textIO.newStringInputReader()
            .read("profeHelio@split--conquer>> ");
    }

    private void loadUI() throws IOException {
        this.ui = FileUtils.readFileToString(logo, StandardCharsets.UTF_8).split("---");
    }

    public void printLogo() {
        System.out.println(ui[0] + "\n");
    }

    public void printHelpTip() {
        System.out.println(ui[1]);
    }

    public void printHelp() {
        System.out.println(ui[2] + "\n" + ui[3]);
    }

    public void commandListener() {
        tio = TextIoFactory.getTextIO();
        new MiscIOUtils().accept(tio, null);
    }

}
