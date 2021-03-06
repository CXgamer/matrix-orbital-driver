import api.API;
import api.Commander;
import api.bitmap.Bitmap;
import api.bitmap.BitmapData;
import api.communication.HardwareControlFlowTriggerLevel;
import api.display.Display;
import com.COMWrapper;
import com.CommunicationException;
import input.InputEventHandler;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import lombok.extern.log4j.Log4j2;
import util.StringUtil;

@Log4j2
public class Communicator {

    private final API api;
    private final InputEventHandler input;

    public Communicator() throws CommunicationException {
        COMWrapper comWrapper = new COMWrapper();
        input = new InputEventHandler();
        comWrapper.setReadHandler(input);

        api = new API(new Commander(command -> {
            log.info(StringUtil.formatBinary("Sending", command));
            try {
                comWrapper.write(command);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }, input), comWrapper);

        setup();
    }

    private void setup() {
        api.communication().reset();
        api.communication().enableHardwareControlFlow(HardwareControlFlowTriggerLevel.BYTES_1);

//        run(resetLCD());
//        comWrapper.resetBaudRate();
//        run(resetLCD());

//        api.communication().changeBaudRate(BaudRate.RATE_115200);
//        log.info("Set baud rate to " + BaudRate.RATE_115200.getBaud());
    }

    public void test() throws CommunicationException {

        Display display = api.display();
        display.setBacklightColour(Color.WHITE);

        api.text().cursor().write("\007");

        api.drawing().clear();

        try {
//            api.bitmaps().drawBitmapDirectly(0, 0,
//                new BitmapData(ImageIO.read(new File("images/sexy_woman.bmp")))
//            );
            Bitmap bitmap = api.bitmaps().createBitmap(new BitmapData(ImageIO.read(new File("images/sexy_woman.bmp"))));

            for (int x = 0; x <= 128; x += 64) {
                bitmap.draw(x, 0);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

//        Label label = api.text().createNewLabel(1, 1, 192, 64, VertPos.MIDDLE, HorPos.MIDDLE, null, false, 1);
//        label.setText("Dag Selina");
//        api.display().setContrast(80, false);

//        Cursor cursor = api.text().cursor();
//        for (int i = 28; i < 127; i++) {
//            cursor.write(Character.toString(((char) i)));
//        }

//        Menu menu = new Menu(api, input);
//        menu.prompt("Welcome to my first menu!",
//            "First option",
//            "Second option",
//            "Third one",
//            "Fourth option",
//            "Fifth",
//            "Sixth",
//            "Seventh",
//            "Eighth");

//        api.drawing().drawer().ellipse(96, 32, 95, 31).filled(true).draw();

//        Label newScrollingLabel = api.text().createNewScrollingLabel(1, 1, 192, 64, VertPos.MIDDLE, Direction.BOUNCE, null, false, 1, 5);
//        newScrollingLabel.setText("Dag Selina!");

//        Bar barGraph = api.drawing().createBarGraph(GraphType.DIRECTION_HORIZONTAL_BASE_LEFT, 1, 1, 32, 64);

//        int chars = 32;
//        Function<Integer, Integer> jitterFunction = input -> input % 0b11 + 1;
//
//        Bar[] bars = new Bar[chars];
//        int[] nums = new int[chars];
//
//        Random r = new Random();
//
//        for (int i = 0; i < chars; i++) {
//            bars[i] = api.drawing().createBarGraph(GraphType.DIRECTION_VERTICAL_BASE_BOTTOM, i * 6 + 1, 1, i * 6 + 6, 64);
//            nums[i] = r.nextInt(64);
//        }
//
//        for (int j = 0; j < 1000; j++) {
//            for (int i = 0; i < chars; i++) {
//                bars[i].updateValue(nums[i]);
//
//                int jitter = jitterFunction.apply(i);
//                nums[i] += r.nextInt(jitter * 2 + 1) - jitter;
//
//                if (nums[i] < 0) nums[i] = 0;
//                else if (nums[i] > 64) nums[i] = 64;
//            }
//
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        }

//        run(setLed(VertPos.BOTTOM, LedState.OFF));
//        run(setLed(VertPos.MIDDLE, LedState.OFF));
//        run(setLed(VertPos.TOP, LedState.OFF));
//
//        run(setLed(VertPos.BOTTOM, LedState.RED));
//
////        run(clear());
////        run(setKeypadBuzzerBeep(440, 100));
////        run(autoTransmitKeyPressesOn());
////        run(clearKeyBuffer());
//
//        run(createLabel(3, 10,  10, 60, 50,  VertPos.MIDDLE, HorPos.MIDDLE, 0,  false, 0));
//        run(updateLabel(3, "CPU"));

//        MusicPlayer musicPlayer = api.buzzer().getMusicPlayer();
//        musicPlayer.playMidi("F:\\Libraries\\Music\\MIDI\\Downloads\\Comptine d un autre été (Kyle Landry).mid", 2f);
//        musicPlayer.playMidi("F:\\Libraries\\Music\\MIDI\\strauss-the-blue-danube.mid", 1f);
//        musicPlayer.playMidi("F:\\Libraries\\Music\\MIDI\\bach-toccata-and-fugue-in-d-minor.mid", 3f);
//
//        int delay = 500;
//        int f0 = 440;
//        int f1 = 523;
//
//        try {
//            for (float polyTime = 50; polyTime > 1; polyTime /= 1.01) {
//                int round = Math.round(polyTime);
//                api.buzzer().activate(f0, round);
//                Thread.sleep(round);
//                api.buzzer().activate(f1, round);
//                Thread.sleep(round);
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//
//        run(setBacklightColor(Color.BLUE));
//        run(setBacklightOn());
//        run(clear());
//
////        run(setLed(VertPos.BOTTOM, LedState.RED));
////        run(echo("test"));
////        run(setLed(VertPos.BOTTOM, LedState.GREEN));
//
////        run(initializeStripChart(0, 0, 0, 191, 63, 0, 100, 1, ChartType.LINE, ChartDirection.BOTTOM_ORIGIN_RIGHT_SHIFT, 0));
////        run(initializeStripChart(0, 171, 53, 191, 63, 0, 100, 1, ChartType.LINE, ChartDirection.BOTTOM_ORIGIN_RIGHT_SHIFT, 0));
////
////        JavaSysMon javaSysMon = new JavaSysMon();
////        CpuTimes oldCpuTime = javaSysMon.cpuTimes();
////        for (;;) {
////            CpuTimes newCpuTime = javaSysMon.cpuTimes();
////            run(updateStripChart(0, ((int) (100 * newCpuTime.getCpuUsage(oldCpuTime)))));
////
////            oldCpuTime = newCpuTime;
////
////
////            Thread.sleep(100);
////        }
//
////        Random r = new Random(System.currentTimeMillis());
////        for (int i = 0; i < 250; i++) {
////            run(updateStripChart(0, r.nextInt(10)));
////
////            run(setBacklightColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255))));
////        }
//
//
//        run(setLed(VertPos.BOTTOM, LedState.GREEN));
    }

//    public void reset() {
//        run(CommunicationCommands.reset());
//        input.blockUntilReceived(Util.hex("fe"), Util.hex("d4"));
//    }
}
