void setup(){
    
    size(800,800);
    colorMode(HSB);
}

float D = 1200;
float c;
float t;

void draw(){ 
    t++;
    //cycle through the spectrum
    if (c>=255)
        c = 0;
    else
        c++;
        
    translate( width/2, height/2);
    drawConcenCirc(20, D, c);
}


/**
  Draws d concentric circles
  @param depth number of concentric objects
  @param D keeps diameter of the circle at step i
  @param c pen color
  @modifies D
**/
void drawConcenCirc(int depth, float D, float c) {
    for ( int i = 0; i <= depth; i++ ) {
        float theta = sin(log(pow(t,3)));
        stroke( (c+5*i)%255, 255, 255);
        fill(   (c+7*i)%255, 255, 255);
        ellipse(0, 0, D, D);
        
        stroke( (c+6*i)%255, 255, 255);
        fill(   (c+8*i)%255, 255, 255);
        rect(0.5*D*cos( (3*PI)/4 ) , -0.5*D*sin( (3*PI)/4 ), D*sin(QUARTER_PI), D*sin(QUARTER_PI));
        rotate(theta);
        D = D*sin((3*PI)/4);
    }    
}
