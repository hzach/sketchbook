void setup() {
  size(600,400);
  strokeWeight(2);
  stroke(25, 20, 15);
  smooth(8);
  background(105, 90, 70);
}



float t;
void draw() {
  t++;
  
  translate(width/2,height/2);
  float alpha = 9./10;
  float theta = acos( height / ( alpha*( width + height ) ) );

}

void ellipseWave(int depth, float alpha, float theta, float t) {
  float x = width;
  float y = height;

  fill(255, 240, 220);
  ellipse(0, 0, x, y);
  for (int i = 1; i <= depth; i++) {
    if (i==depth)
      noFill();
    else
      fill(255-10*i, 240-10*i, 220-10*i);
      
    x *= alpha;
    y *= alpha;
    rotate( PI/16*(sin(PI/512*t) ) );
    ellipse(0, 0, x, y);
  }
}

