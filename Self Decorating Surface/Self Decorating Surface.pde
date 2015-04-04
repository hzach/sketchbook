void setup(){
  size(2000,600);
  background(255);
  

}
float alpha = 13;
float phase1 = -11;
float freq1 = 60;
float phase2 = -12 ;
float freq2 = 60;
int m = 8;
float x;
float y;

void draw(){
   background(255);
   for (float i = 0; i < width; i++){
     for (float j = 0; j < height; j++){
       x = phase1 + freq1*i;
       y = phase2 + freq2*j;
       float z = alpha*( sin(x) + sin(y) );
       int c = (int) truncate(z);
       //print(c+" ");
       if ( abs(c) % m == 0 ){
         stroke(0);
         fill(noise(i,j)*255, noise(j,i)*255, noise(x,y)*255);
         ellipse(i, j, 2, 2);
     }
   }
  }
  saveFrame("images/f###.jpg");
  if (frameCount==1)
    exit();
}

float truncate(float x){
  if ( x > 0 )
    return float(floor(x * 100))/100;
  else
    return float(ceil(x * 100))/100;
  }

  
