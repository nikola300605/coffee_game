package org.systempro.project.verlet2d;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class CollisionColumnThread implements Runnable {

    private final ArrayList<Particle>[][] cells;
    private final int i;

    public CollisionColumnThread(ArrayList<Particle>[][] cells,int i){
        this.cells=cells;
        this.i=i;
    }

    @Override
    public void run() {
        for (int j = 0; j < cells[i].length; j++) {
            for (Particle p : cells[i][j]) {
                collideWithNearCells(p, i, j);
            }
        }
    }

    private void collideWithNearCells(Particle p1,int x,int y){
        for(int i=-1;i<=1;i++){
            for(int j=-1;j<=1;j++){
                if(x+i<0||x+i>=cells.length||y+j<0||y+j>=cells[0].length)continue;
                for(Particle p2:cells[x+i][y+j]){
                    if(p1==p2)continue;
                    Particle.resolveCollision(p1,p2);
                }
            }
        }
    }
}
