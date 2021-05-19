

import java.awt.*;
import java.util.ArrayList;

/**
 * Write a description of class Snake here.
 *
 * @author Abdul Majid
 * @version 24-04-2021
 */
public class AM_Snake
{
    public static final String UP = "up";
    public static final String DOWN = "down";
    public static final String LEFT = "left";
    public static final String RIGHT = "right";

    ArrayList<AM_SnakePart> body;
    String direction;
    int x_cordinate;
    int y_cordinate;
    int size;
    int pixelsToMove;
    int length;

    /**
     * Constructor for objects of class Snake
     */
    public AM_Snake(int x, int y) {
        size = 10;
        x_cordinate = x;
        y_cordinate = y;
        pixelsToMove = 10;
        direction = UP;
        length = 2;
        initSnakeBody(length);
    }

    public void move(String direction, int minX, int minY, int maxX, int maxY) {
        if (direction != null) {
            changeDirection(direction);
        }

        moveHead(minX, minY, maxX, maxY);

        String leaderDirection = this.direction;
        for (int i = 0; i < body.size(); i++) {
            body.get(i).move(pixelsToMove, minX, minY, maxX, maxY);
            String temp = body.get(i).getDirection();
            body.get(i).setDirection(leaderDirection);
            leaderDirection = temp;
        }
    }

    public void grow() {
        AM_SnakePart tail = body.get(body.size()-1);
        AM_SnakePart newTail = new AM_SnakePart(tail.getDirection(), tail.getXCoordinate(), tail.getYCoordinate());
        switch (tail.getDirection()) {
            case UP:
                newTail.setYCoordinate(tail.getYCoordinate() + size);
                break;
            case DOWN:
                newTail.setYCoordinate(tail.getYCoordinate() - size);
                break;
            case LEFT:
                newTail.setXCoordinate(tail.getXCoordinate() - size);
                break;
            case RIGHT:
                newTail.setXCoordinate(tail.getXCoordinate() + size);
                break;
        }
        body.add(newTail);
        length++;
    }

    private void moveHead(int minX, int minY, int maxX, int maxY) {
        switch (this.direction) {
            case UP:
                y_cordinate -= pixelsToMove;
                break;
            case DOWN:
                y_cordinate += pixelsToMove;
                break;
            case LEFT:
                x_cordinate += pixelsToMove;
                break;
            case RIGHT:
                x_cordinate -= pixelsToMove;
                break;
        }

        if (y_cordinate < minY) {
            y_cordinate = maxY;
        } else if (y_cordinate > maxY) {
            y_cordinate = minY;
        } else if (x_cordinate < minX) {
            x_cordinate = maxX;
        } else if (x_cordinate > maxX) {
            x_cordinate = minX;
        }
    }

    private void changeDirection(String direction) {
        if (
            this.direction.equals(UP) && direction.equals(DOWN) ||
            this.direction.equals(LEFT) && direction.equals(RIGHT) ||
            this.direction.equals(DOWN) && direction.equals(UP) ||
            this.direction.equals(RIGHT) && direction.equals(LEFT)
        ) {
            // can not move in the opposite direction
            return;
        }

        this.direction = direction;
    }

    private void initSnakeBody(int length) {
        body = new ArrayList<>();
        body.add(new AM_SnakePart(direction, x_cordinate, y_cordinate + size));
        for (int i = 1; i < length; i++) {
            body.add(new AM_SnakePart(direction, x_cordinate, body.get(i-1).getYCoordinate() + size));
        }
    }

    public boolean didSnakeEatItself() {
        Rectangle head = new Rectangle(x_cordinate, y_cordinate, size, size);
        for (int i = 0; i < body.size(); i++) {
            AM_SnakePart snakePart = body.get(i);
            if (head.intersects(new Rectangle(snakePart.getXCoordinate(), snakePart.getYCoordinate(), size, size))) {
                return true;
            }
        }

        return false;
    }

    public boolean didSnakeEatPrey(AM_Prey prey) {
        Rectangle preyRectangle = new Rectangle(prey.x_cordinate, prey.y_cordinate, prey.size, prey.size);
        Rectangle snakeRectangle = new Rectangle(x_cordinate, y_cordinate, size, size);

        return preyRectangle.intersects(snakeRectangle);
    }
}
