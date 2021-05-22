public class AM_SnakePart {
    private String direction;
    private int x_coordinate;
    private int y_coordinate;

    public AM_SnakePart(String direction, int x_coordinate, int y_coordinate) {
        this.direction = direction;
        this.x_coordinate = x_coordinate;
        this.y_coordinate = y_coordinate;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getXCoordinate() {
        return x_coordinate;
    }

    public void setXCoordinate(int x_coordinate) {
        this.x_coordinate = x_coordinate;
    }

    public int getYCoordinate() {
        return y_coordinate;
    }

    public void setYCoordinate(int y_coordinate) {
        this.y_coordinate = y_coordinate;
    }

    public void move(int speed) {
        switch (direction) {
            case AM_Snake.UP:
                y_coordinate -= speed;
                break;
            case AM_Snake.DOWN:
                y_coordinate += speed;
                break;
            case AM_Snake.LEFT:
                x_coordinate += speed;
                break;
            case AM_Snake.RIGHT:
                x_coordinate -= speed;
                break;
        }
    }
}
