package Attack.com.puzzle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import static java.lang.Math.round;

public class PuzzleView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    int currentTime=0;


	// Declaration des images
    private Bitmap 		block;
    private Bitmap 		perso;
    private Bitmap 		perso1;
    private Bitmap 		perso2;


    private Bitmap 		vide;    
    private Bitmap[] 	zone = new Bitmap[4];
    private Bitmap 		win;

    int matrice[][]=new int[10][10];

    static float currentX=0;
    PosX test;


    // Declaration des objets Ressources et Context permettant d'accéder aux ressources de notre application et de les charger
    private Resources 	mRes;    
    private Context 	mContext;

    // tableau modelisant la carte du jeu
    int[][] carte;

    int k,l;
    
    // ancres pour pouvoir centrer la carte du jeu
    int        carteTopAnchor;                   // coordonnées en Y du point d'ancrage de notre carte
    int        carteLeftAnchor;                  // coordonnées en X du point d'ancrage de notre carte

    // taille de la carte
    static final int    carteWidth    = 9;
    static final int    carteHeight   = 9;
    static final int    carteTileSize = 30;
    //les effets du jeu
    MediaPlayer media = MediaPlayer.create(getContext(), R.raw.bruit);
    MediaPlayer move = MediaPlayer.create(getContext(), R.raw.laser);

    // constante modelisant les differentes types de cases
    static final int    CST_block     = 0;
    static final int    CST_zone      = 3;
    static final int    CST_vide      = 4;

    // tableau de reference du terrain
    public int [][] ref = Level.terrain1();

  /* compteur et max pour animer les zones d'arriv�e des diamants */
        int currentStepZone = 0;
        int maxStepZone     = 4;  

        // thread utiliser pour animer les zones de depot des diamants
        private     boolean in      = true;
        private     Thread  cv_thread;        
        SurfaceHolder holder;
        
        Paint paint;

    /**
     * The constructor called from the main JetBoy activity
     *
     * @param context
     * @param attrs
     */
    public PuzzleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        test=new PosX(0,0);
        test.setK(0);

        // permet d'ecouter les surfaceChanged, surfaceCreated, surfaceDestroyed
    	holder = getHolder();
        holder.addCallback(this);


        // chargement des images
        mContext	= context;
        mRes 		= mContext.getResources();        
        block 		= BitmapFactory.decodeResource(mRes, R.drawable.cadre);
    	perso		= BitmapFactory.decodeResource(mRes, R.drawable.fb);
        perso1		= BitmapFactory.decodeResource(mRes, R.drawable.twitter);
        perso2		= BitmapFactory.decodeResource(mRes, R.drawable.google);
    	vide 		= BitmapFactory.decodeResource(mRes, R.drawable.blue);

    	win 		= BitmapFactory.decodeResource(mRes, R.drawable.win);


    	// initialisation des parmametres du jeu
    	initparameters();

    	// creation du thread
        cv_thread   = new Thread(this);
        // prise de focus pour gestion des touches
        setFocusable(true);

    }

    // chargement du niveau a partir du tableau de reference du niveau
    private void loadlevel() {
    	for (int i=0; i< carteHeight; i++) {
            for (int j=0; j< carteWidth; j++) {
                carte[j][i]= ref[j][i];
            }
        }	
    }    
    
    // initialisation du jeu
    public void initparameters() {
        paint = new Paint();
    	paint.setDither(true);
    	paint.setStyle(Paint.Style.STROKE);
    	paint.setStrokeJoin(Paint.Join.ROUND);
    	paint.setStrokeCap(Paint.Cap.ROUND);
    	paint.setStrokeWidth(3);    	
    	paint.setTextAlign(Paint.Align.LEFT);
        carte           = new int[carteHeight][carteWidth];
        loadlevel();
        carteTopAnchor  = (getHeight()- carteHeight*carteTileSize)/2;
        carteLeftAnchor = (getWidth()- carteWidth*carteTileSize)/2;

        for (int i=0; i< 4; i++) {
       //     diamants[i][1] = refdiamants[i][1];
        //    diamants[i][0] = refdiamants[i][0];
        }
        if ((cv_thread!=null) && (!cv_thread.isAlive())) {
        	cv_thread.start();
        	Log.e("-FCT-", "cv_thread.start()");
        }
    }

// donner la gravitation pour les caillots
    public void gravity(){
        int aux;

           for (int j = 0; j <= 7; j++) {
                for (int i=0;i<=7;i++) {
                    for(int s=0;s<=6-i;s++){
                        if ((matrice[s][j]>matrice[s+1][j])&&(matrice[s+1][j]==0)){
                            aux=matrice[s+1][j];
                            matrice[s+1][j]=matrice[s][j];
                            matrice[s][j]=aux;
                        }
                    }
                }
           }
    }


    // dessin des les caillots
    private void paintarrow(Canvas canvas) {
        for (int i=0;i<=7;i++) {
            for (int j = 0; j <= 7; j++) {

                if (matrice[i][j]==1) {
                    canvas.drawBitmap(perso,carteLeftAnchor+ j*carteTileSize, carteTopAnchor+ i*carteTileSize, null);
                }
                if (matrice[i][j]==2) {
                    canvas.drawBitmap(perso1,carteLeftAnchor+ j*carteTileSize, carteTopAnchor+ i*carteTileSize, null);
                }
                if (matrice[i][j]==3) {
                    canvas.drawBitmap(perso2,carteLeftAnchor+ j*carteTileSize, carteTopAnchor+ i*carteTileSize, null);
                }
            }
        }
    }



    // dessin du gagne si gagne
    private void paintwin(Canvas canvas) {
    	canvas.drawBitmap(win, carteLeftAnchor+ 3*carteTileSize, carteTopAnchor+ 4*carteTileSize, null);
    }
    
    // dessin de la carte du jeu
    private void paintcarte(Canvas canvas) {
    	for (int i=0; i< carteHeight; i++) {
            for (int j=0; j< carteWidth; j++) {
                switch (carte[i][j]) {
                    case CST_block:
                    	canvas.drawBitmap(block, carteLeftAnchor+ j*carteTileSize, carteTopAnchor+ i*carteTileSize, null);
                    	break;                    
                    case CST_zone:
                    	canvas.drawBitmap(zone[currentStepZone],carteLeftAnchor+ j*carteTileSize, carteTopAnchor+ i*carteTileSize, null);
                        break;
                    case CST_vide:
                    	canvas.drawBitmap(vide,carteLeftAnchor+ j*carteTileSize, carteTopAnchor+ i*carteTileSize, null);
                        break;
                }
            }
        }
    }
    
    // dessin le temps
    private void paintTime(Canvas canvas) {
        MainActivity.time.setText(currentTime);
    }


    // casse les caillots

    private void casseCai(){
        int s=0;
        int x=0;
        int y=0;

        Log.e("CASSE CAI",":");
        for (int i=0;i<=7;i++) {
            for (int j=0;j<=7;j++) {
                if(matrice[i][j]==1){
                    s++;
                    x=0;
                    y=0;
                    Log.e("CASSE CAI", String.valueOf(s));
                    if (s==3){
                        matrice[i][j]=0;
                        matrice[i][j-1]=0;
                        matrice[i][j-2]=0;
                        //score.setText(x);
                        media.start();

                    }

                }
                else if (matrice[i][j]==2){
                    s=0;
                    y=0;
                    x++;
                    if (x==3){
                        matrice[i][j]=0;
                        matrice[i][j-1]=0;
                        matrice[i][j-2]=0;
                        //score.setText(x);
                        media.start();

                    }

                }
                else if (matrice[i][j]==3){
                    x=0;
                    s=0;
                    y++;
                    if (y==3){
                        matrice[i][j]=0;
                        matrice[i][j-1]=0;
                        matrice[i][j-2]=0;
                        //score.setText(x);
                        media.start();

                    }

                }
                else {
                    x=0;
                    y=0;
                    s=0;
                }

            }
            x=0;
            y=0;
            s=0;
        }
    }

// on teste si on a gagne ou non

    private boolean isWon() {
        int x=0;
        int y=0;
    for (int i=0;i<=7;i++)
        for (int j=0;j<=7;j++)
            if (matrice[i][j]!=0) return false;

            return true;
    }
    
    // dessin du jeu (fond uni, en fonction du jeu gagne ou pas dessin du plateau et du joueur des diamants et des fleches)
    private void nDraw(Canvas canvas) {
		canvas.drawRGB(00,00,00);
        	paintcarte(canvas);
            paintarrow(canvas);
            paintTime(canvas);
         if (isWon()) paintwin(canvas);




    }
    
    // callback sur le cycle de vie de la surfaceview
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    	initparameters();
    }

    public void surfaceCreated(SurfaceHolder arg0) {
    	Log.i("-> FCT <-", "surfaceCreated");    	        
    }

    
    public void surfaceDestroyed(SurfaceHolder arg0) {
    	Log.i("-> FCT <-", "surfaceDestroyed");    	        
    }    


//intialisation de matrice et vider les caillots dans le jeu
    public void initMatrice(){
        for (int i=0;i<=7;i++) {
            for (int j = 0; j <= 7; j++) {
                matrice[i][j]=0;
            }

        }

    }


    public void run() {
        //initialiser la gravitation
        gravity();



        Canvas c = null;
        while (in) {

            try {
                cv_thread.sleep(40);
                currentTime=currentTime+40;
                test.setIj(0,0);
                currentStepZone = (currentStepZone + 1) % maxStepZone;
                try {
                    c = holder.lockCanvas(null);
                    if (c!=null) {
                        nDraw(c);
                    }

                } finally {
                	if (c != null) {
                		holder.unlockCanvasAndPost(c);
                    }
                }
            } catch(Exception e) {
            	Log.e("-> RUN <-", e.toString());
            }
        }
    }
    
//selectionner et bouger le caillot a gauche
    private void getNewPositionLeft() {
        int aux;
        int j=test.getI();
        int i=test.getJ();
        Log.e("matrice", String.valueOf(matrice[i][j]));
        Log.e("getI", String.valueOf(i));
        Log.e("getJ", String.valueOf(j));
        if ((matrice[i][j]!=0)&&(matrice[i][j-1]==0)&&(j-1>0)) {
            move.start();
            aux=matrice[i][j];
            matrice[i][j] = matrice[i][j - 1];
            matrice[i][j - 1] = aux;
            gravity();

        }
        test.setIj(0,0);
    }
//selectionner et bouger le caillot a gauche

    private void getNewPositionRigth() {
        int aux;
        int j=test.getI();
        int i=test.getJ();
        Log.e("matrice", String.valueOf(matrice[i][j]));
        Log.e("getI", String.valueOf(i));
        Log.e("getJ", String.valueOf(j));
        if ((matrice[i][j]!=0)&&(matrice[i][j+1]==0)&&(j+1<8)) {
            move.start();
            aux=matrice[i][j];
            matrice[i][j] = matrice[i][j + 1];
            matrice[i][j + 1] = aux;
            gravity();

        }
        test.setIj(0,0);
    }

    
    // fonction permettant de recuperer les evenements tactiles
    @SuppressLint("NewApi")
    public boolean onTouchEvent (MotionEvent event) {

        int x=round(event.getX()-carteLeftAnchor)/carteTileSize;
        int y=round(event.getY()- carteTopAnchor)/carteTileSize;
        if (test.getIJ()==0)
            test.setIj(x,y);

        if (currentX==0) currentX=event.getX();
        Log.e("Current x:", String.valueOf(test.getK()-event.getX()));
        Log.e("get x:", String.valueOf(event.getX()));

        Log.i("-> FCT <-", "onTouchEvent: "+ event.getX());

    	Log.e("position:", String.valueOf((test.getK()-event.getX())));

    	//detecter la translation de doit soit a gauche ou droite
         if (test.getK()-event.getX()<(-0.2) && ((-1)<test.getK()-event.getX())){getNewPositionRigth();};
         if (test.getK()-event.getX()>(0.2) && ((1)>test.getK()-event.getX())){getNewPositionLeft();};

        //casse les caillots
        casseCai();
        //enregistrer la position du doit
        test.setK(event.getX());

    	return true;
    }
}
