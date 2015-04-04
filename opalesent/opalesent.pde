void setup() {
  size(640,480, JAVA2D); 
  noiseDetail(2);
  smooth(8);
  //frameRate(30);
}

float t=0;
void draw() {
  t+=0.01;

  loadPixels();
  float noiseScale = 0.005;
  for (int i=0; i < width; i++) {
    for (int j=0; j < height; j++){
      pixels[i+width*j] = color(noise(i*noiseScale + t, j*noiseScale + t, 2*noiseScale*(i+j)+2*t)*255);
    }
  }

  for (int i=0; i < width; i++) {
    for (int j=0; j < height; j++){
      pixels[i+width*j] *= color(50*sin(PI/1029*pixels[i+width*j]));
    }
  }
  
  updatePixels();
  
}


