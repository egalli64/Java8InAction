package dd.ch09;

import java.util.Arrays;
import java.util.List;

public class P1EvolvingApis {
    public static void main(String[] args) {
        List<Resizable> resizableShapes = Arrays.asList(new Square(), new Triangle(),
                new Ellipse());
        Utils.paint(resizableShapes);
    }
}

interface Drawable {
    public void draw();
}

interface Resizable extends Drawable {
    int getWidth();

    int getHeight();

    void setWidth(int width);

    void setHeight(int height);

    void setAbsoluteSize(int width, int height);

    // when added in version 2 it would usually break the user code!
    // void setRelativeSize(int widthFactor, int heightFactor);
    
    // default modifier saves the day
    default void setRelativeSize(int wFactor, int hFactor) {
        setAbsoluteSize(getWidth() / wFactor, getHeight() / hFactor);
    }
}

// user code base on our library

class Ellipse implements Resizable {
    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public void setWidth(int width) {
    }

    @Override
    public void setHeight(int height) {
    }

    @Override
    public void setAbsoluteSize(int width, int height) {
        System.out.println("Setting ellipse absolute size to " + width + ", " + height);
    }

    @Override
    public void draw() {
    }
}

class Triangle implements Resizable {
    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public void setWidth(int width) {
    }

    @Override
    public void setHeight(int height) {
    }

    @Override
    public void setAbsoluteSize(int width, int height) {
        System.out.println("Setting triangle absolute size to " + width + ", " + height);
    }

    @Override
    public void draw() {
    }
}

class Square implements Resizable {
    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public void setWidth(int width) {
    }

    @Override
    public void setHeight(int height) {
    }

    @Override
    public void setAbsoluteSize(int width, int height) {
        System.out.println("Setting square absolute size to " + width + ", " + height);
    }

    @Override
    public void draw() {
    }
}

class Utils {
    public static void paint(List<Resizable> list) {
        list.forEach(r -> {
            r.setAbsoluteSize(42, 42);
        });

        // l.forEach(r -> { r.setRelativeSize(2, 2); });
    }
}
