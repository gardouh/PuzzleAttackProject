package Attack.com.puzzle;

/**
 * Created by mac on 11/21/17.
 */

public class PosX {
    int  i,j;
    float k;

    public PosX() {
    }

    public PosX(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public float getK() {
        return k;
    }

    public void setK(float k) {
        this.k = k;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }
    public int getIJ() {
        return j+i;
    }

    public void setIj(int i,int j) {
        this.i = i;
        this.j = j;

    }


}
