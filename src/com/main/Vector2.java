package com.main;
public class Vector2 {
    public int x;
    public int y;
    public Vector2(int x, int y){
        this.x = x;
        this.y = y;
    }
        public Vector2 add(Vector2 other) {
        return new Vector2(this.x + other.x, this.y + other.y);
    }

}
