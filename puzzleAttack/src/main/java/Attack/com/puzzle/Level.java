package Attack.com.puzzle;

import static Attack.com.puzzle.PuzzleView.CST_block;
import static Attack.com.puzzle.PuzzleView.CST_vide;
import static Attack.com.puzzle.PuzzleView.CST_zone;


/**
 * Created by mac on 11/1/17.
 */

public  class Level {
    private  int ref[][];

    static int [][] terrain1(){
        int [][] ref    = {
                {CST_block, CST_block, CST_block,CST_block, CST_block, CST_block, CST_block, CST_block, CST_block},
                {CST_block, CST_vide, CST_vide,CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_block},
                {CST_block, CST_vide, CST_vide,CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_block},
                {CST_block, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_block},
                {CST_block, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_block},
                {CST_block, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_block},
                {CST_block, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_block},
                {CST_block, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_block},
                {CST_block, CST_block, CST_block,CST_block, CST_block, CST_block, CST_block, CST_block, CST_block},

        };

        return  ref;
    }

    static int [][] terrain2(){
        int [][] ref    ={
                {CST_vide, CST_block, CST_block,CST_block, CST_block, CST_block, CST_block, CST_block, CST_block, CST_vide, CST_block},
                {CST_block, CST_block, CST_block,CST_vide, CST_vide, CST_vide, CST_vide, CST_block, CST_block, CST_block , CST_block},
                {CST_block, CST_vide, CST_vide,CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_block , CST_block},
                {CST_block, CST_vide, CST_vide, CST_block, CST_vide, CST_vide, CST_block, CST_vide, CST_vide, CST_block , CST_block},
                {CST_block, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_block , CST_block},
                {CST_block, CST_vide, CST_block, CST_vide, CST_vide, CST_vide, CST_vide, CST_block, CST_vide, CST_block , CST_block},
                {CST_block, CST_vide, CST_vide, CST_vide, CST_block, CST_block, CST_vide, CST_vide, CST_vide, CST_block , CST_block},
                {CST_block, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_block , CST_block},
                {CST_block, CST_block, CST_vide,CST_zone, CST_zone, CST_zone, CST_zone, CST_vide, CST_block, CST_block , CST_block},
                {CST_vide, CST_block, CST_block,CST_block, CST_block, CST_block, CST_block, CST_block, CST_block, CST_vide , CST_block}
        };
        return  ref;
    }


    static int[][] refdiamants1()
    {
       int [][] refdiamants={
               {6, 3},
               {6, 4},
               {6, 5},
               {6, 6}
        };


        return refdiamants;
    }


    static int[][] refdiamants2()
    {
        int [][] refdiamants={
                {2, 3},
                {2, 4},
                {2, 5},
                {2, 6}
        };


        return refdiamants;
    }





}
