import controlP5.*;
ControlP5 cp5;

/**
Boundary points of (x(t),y(t)) plane
*/
float xmin = -6.2; float xmax = 6.1;
float ymin = -2.2; float ymax = 2.1;
int RES = 50; //Point resolution
float M = (xmax-xmin)/RES; //Step size in x(t)
float P = (ymax-ymin)/RES; //Step size in y(t)

/**
Numerical method parameters
*/
int N = 20 ;      //Maximum iterations 
float h = 0.025 ; //step size
Method m = Method.RK4;

//drawspeed
int timeInterval = 100;
int timeSinceLastEvent = 0;
/**
Color settings of ellipses at a given stage of orbit
*/
void colorPoint(int i) {
  stroke(100 + 2*i, 255 - i, 255 - 2*i, 75);
  fill(100 + 2*i, 255 - i, 255 - 2*i, 75);
}

//////////////////////////////////////////////////////////////
/**
Preamble
*/
void setup() {
  size(1800, 940, JAVA2D);
  background(0);
  noSmooth();
  noLoop();
}

void draw() {
  translate(width/2,height/2);
 // rotate(PI/4);

  for (float i = xmin; i < xmax; i = i + M) {
    for(float j = ymin; j < xmax; j = j + P) {
      //fill(0,2);
      //rect( -width/2,-height/2, width,height);
      switch(m){
        case FORWARD_EULER:
          euler0(i,j);
        case RK4:
          RK4(i,j);
        case IMPLICIT_EULER:
          implicitEuler(i,j);
      }
    }
  }
  saveFrame("snapshot" + hour() + ":" + minute() + ":" + second()+ ".jpg");
}
////////////////////////////////////////////////////////////////


/**
The forward Euler method that computes solutions to the system
  x'(t) = -f(y(t))
  y'(t) = f(x(t)).
Given the initial conditions (x(t0),y(t0)) = (x0,y0), places a 
small ellipse at each successive (xt+1, yt+1) N times with step 
size h.
@param x0 initial point in x(t).
@param y0 initial point in y(t).
*/
void euler0(float x0, float y0){
  float x = x0;
  float y = y0;

  for (int i = 0; i < N; i++) {
    float xi = -h*f(y) + x; float yi =  h*f(x) + y; 
    colorPoint(i);
    x = xi; y = yi;
    xi = map(xi, xmin, xmax, -width/2.0, width/2.0);
    yi = map(yi, ymin, ymax, -height/2.0, height/2.0); 
    ellipse( xi, yi, 2, 2 );

 } 
}

/**
The RK$ method which computes solutions to the system
  x'(t) = -f(y(t))
  y'(t) = f(x(t)).
Given the initial conditions (x(t0),y(t0)) = (x0,y0), places a 
small ellipse at each successive (xt+1, yt+1) N times with step 
size h. Works well with small step size (h ~ 0.001) and low 
N ( N < 50 ).
@param x0 initial point in x(t).
@param y0 initial point in y(t).
**/
void RK4( float x0, float y0) {
  float x = x0;
  float y = y0;
  for (int i = 0; i < N; i++){
    float kx1 = h*( -f(y) ); float ky1 = h*f(x);
    float kx2 = h*( -f( y + kx1/2 ) ); float ky2 = h*f( x + ky1/2 );
    float kx3 = h*( -f( y + kx2/2 ) ); float ky3 = h*f( x + ky2/2 );
    float kx4 = h*( -f( y + kx3 ) ); float ky4 = h*f( x + ky3 );
    float xi = x + ( kx1 + 2*kx2 + 2*kx3 + kx4 ); float yi = y + ( ky1 + 2*ky2 + 2*ky3 + ky4 );
    x = xi; y = yi;
    xi = map(xi, xmin, xmax, -width/2.0, width/2.0);
    yi = map(yi, ymin, ymax, -height/2.0, height/2.0); 
    colorPoint(i);
    ellipse( xi, yi, 2, 2 );
  }
}

/**
h~0.2 - 0.4
*/
void implicitEuler(float x0, float y0) {
  float x = x0;
  float y = y0;

  for (int i = 0; i < N; i++) {
    float xi = -h*f( y + h*f(y) ) + x; float yi =  h*f(x) + y; 
    colorPoint(i);
    x = xi; y = yi;
    xi = map(xi, xmin, xmax, -width/2.0, width/2.0);
    yi = map(yi, ymin, ymax, -height/2.0, height/2.0); 
    ellipse( xi, yi, 2, 2 );
  }
}

/**
Computes the value of the function
  f_n(x) = sin( x + sin(rho*x) ).
at the point x.
@param x input value of f.
@returns the value of f(x).
*/
float f( float x ) {
  float rho = 3.0;
  return sin( pow(x,1) + pow( tan( rho*pow(x,1) ),1));
}
