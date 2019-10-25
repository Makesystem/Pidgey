
import com.makesystem.pidgey.console.Console;
import com.makesystem.pidgey.io.file.FilesHelper;
import com.makesystem.pidgey.lang.StringHelper;
import com.makesystem.pidgey.tester.AbstractTester;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author riche
 */
public class Extract_Google_Icons_Tester extends AbstractTester {

    public static void main(String[] args) {
        new Extract_Google_Icons_Tester().run();
    }

    @Override
    protected void preExecution() {
    }

    @Override
    protected void execution() {
        try {

            final Collection<String> iconsStorage = new LinkedHashSet<>();
            iconsStorage.add("");

            readCodepoints("E:\\codepoints", iconsStorage);
            readMaterialIoIconsPage("E:\\icons_google.html", iconsStorage);

            final String enums = iconsStorage.stream().sorted().map(icon -> buildEnum(icon)).collect(Collectors.joining("," + StringHelper.LF)) + ";";

            Console.log("Lines: {i}", iconsStorage.size());
            Console.log("{s}", enums);

        } catch (final Throwable throwable) {
        }

    }

    protected String buildEnum(final String icon) {
        return enumName(icon) + "(\"" + icon + "\")";
    }

    protected String enumName(final String icon) {
        return icon.isEmpty() ? "DEFAULT" : (Character.isDigit(icon.charAt(0)) ? "_" : "") + icon.toUpperCase();
    }

    protected void readCodepoints(final String file, final Collection<String> iconsStorage) throws IOException {
        FilesHelper.readLines(file, line -> iconsStorage.add(line.split(" ", -1)[0]));
    }

    protected void readMaterialIoIconsPage(final String file, final Collection<String> iconsStorage) throws IOException {

        final String elementStart = "<i _ngcontent-xkn-c19=\"\" class=\"material-icons icon-image-preview\">";
        final String elementEnd = "</i></icons-image>";

        FilesHelper.readLines(file, line -> {
            if (line.contains("material-icons icon-image-preview")) {

                final String icon = line
                        .replace(elementStart, "")
                        .replace(elementEnd, "")
                        .replace(" ", "")
                        .replace("\t", "");

                iconsStorage.add(icon);
            }
        });
    }

    @Override
    protected void posExecution() {

    }

}
