import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by sam on 10/30/14.
 */
public class GravityAnimator implements ActionListener {
    private boolean destroyAfterUse;
    private Timer time;
    private int WIDTH, LENGTH;

    private int elapsedTime;
    private int stopTime;

    private dblPoint startLocation;

    public class dblPoint {
        public double x, y;

        public dblPoint(double x, double y) {
            this.x = x;
            this.y = y;
        }
    };

    private JComponent sprite;

    dblPoint pos;
    dblPoint speed;

    public GravityAnimator(JComponent sprite, int WIDTH, int LENGTH, boolean destroyAfterUse) {
        this.startLocation = new dblPoint(sprite.getLocation().x, sprite.getLocation().y);
        this.WIDTH = WIDTH;
        this.LENGTH = LENGTH;
        this.sprite = sprite;
        this.pos = new dblPoint(sprite.getLocation().x, sprite.getLocation().y);
        this.speed = new dblPoint(10.0,-1.0);
        this.elapsedTime = 0;
        this.stopTime = 1500;
        this.destroyAfterUse = destroyAfterUse;
    }

    public GravityAnimator(JComponent sprite, int WIDTH, int LENGTH, boolean destroyAfterUse, int speedx, int speedy) {
        this.startLocation = new dblPoint(sprite.getLocation().getX(), sprite.getLocation().getY());

        this.WIDTH = WIDTH;
        this.LENGTH = LENGTH;
        this.sprite = sprite;
        this.pos = new dblPoint(sprite.getLocation().x, sprite.getLocation().y);
        this.speed = new dblPoint(speedx, speedy);
        this.elapsedTime = 0;
        this.stopTime = 1500;
        this.destroyAfterUse = destroyAfterUse;
    }

    public void animate(int stopTime) {
        int delay = 20; //milliseconds
        this.stopTime = stopTime;
        this.time = new Timer(delay, this);
        this.time.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        elapsedTime += 20;

        if (pos.y < LENGTH - sprite.getHeight() && pos.y > 0) {
            pos.y += speed.y;
            sprite.setLocation((int)pos.x, (int)pos.y);
            speed.y += (speed.y < 0) ? .5 : .3;
        } else {
            speed.y *= -1;
            if (pos.y < 0) pos.y = 1;
            else pos.y = LENGTH - sprite.getHeight() - 1;
            sprite.setLocation((int)pos.x, (int)pos.y);
        }

        if (pos.x < WIDTH - sprite.getWidth() && pos.x > 0){
            pos.x += speed.x;
            sprite.setLocation((int)pos.x, (int)pos.y);
        } else {
            speed.x *= -1;
            if (pos.x <0) pos.x = 1;
            else pos.x = WIDTH - sprite.getWidth() - 1;
            sprite.setLocation((int)pos.x, (int)pos.y);
        }
        if (elapsedTime > stopTime) {
            this.time.stop();
            elapsedTime = 0;
            this.sprite.setLocation((int)startLocation.x, (int)startLocation.y);
            this.pos = startLocation;
            if (destroyAfterUse) {
                this.sprite.setVisible(false);
                this.sprite.getParent().remove(this.sprite);
            }
        }
    }

}
