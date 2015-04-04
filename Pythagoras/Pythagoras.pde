PImage bg;
void setup(){
  size(1000,1000);
  colorMode(HSB);
  smooth(4);
  bg = loadImage("galaxyresized.jpg");
}
int depth = 100;
int t=1;
int c;
void draw(){
  frameRate(60);
  t++;
  background(bg);
  translate(width/2,height/2);
  float theta = PI/(32-16*sin((PI/1028)*t));
  squareSpiral(theta, depth);

}

void squareSpiral(float theta, int depth) {
  float alpha = width;
  for (int i = 0 ; i <= depth; i++){
    
    float gamma = alpha*tan(theta)/(1+tan(theta));
    float beta = alpha - gamma;
    float delta = sqrt(pow(gamma,2)+pow(beta,2)); 
    rectMode(CENTER);
    rotate(theta);
    stroke(  255*pow( sin( PI/1028*( t+6*i ) ), 2 ), 150, 150);
    fill( 
          255*pow( sin( PI/1028*(t+6*i) ), 2 ),
          100, 
          255*pow( cos( PI/1028*( t+6*i ) ), 2 ), 
          50*pow( cos( PI/1028*( t+6*i ) ), 2 ) + 50 
         );

    rect(0,0,delta,delta);
    alpha = delta;
  }
}
  
