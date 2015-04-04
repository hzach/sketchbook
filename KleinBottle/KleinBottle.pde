import rules.*;
import cellery.*;
import topology.*;

float theta;

//Circumference of the inner-circle coplanar with the center point of the torus
int klnMajor;
//Circumference of the tube
int klnMinor;

//Dimensions of the grid 
int lifeWidth;
int lifeHeight;
int cellSize;
int density;

//The amount of time between iterations of the CA in milliseconds
int timeInterval = 100;

//the previoud runtime in milliseconds
int lastRunTime = 0;

/**
*CA ingredients:The underlying space that the automaton lives in, the topology on that space and
*finally the rule that governs the automaton. 
*/
Topology2D topo;
CellArray2D seeds;
Rule life;
BinaryAutomaton2D ca;

void setup() {
  size(600, 600, P3D);
  
  //assign mobius band geometry values
  klnMajor = 400;
  klnMinor = 300;
  
  //Assign grid geometry values
  cellSize = 5;
  lifeWidth = klnMajor/cellSize;
  lifeHeight = klnMinor/cellSize;
  density = 80;

  //Assemble CA ingredients
  topo = new Topology2D(Topology2D.Base.MOORE, 1, Topology2D.Space.KLN);
  //seeds = new CellArray2D( CellerySeeds.randomOnes(density, lifeWidth, lifeHeight), topo );
  life = new Rule(new int[] {3}, new int[] {2,3});
  //ca = new BinaryAutomaton2D(seeds, life);

  noFill();
  stroke(40);
  smooth(8);
}

void draw() { 
  //int t = ca.step();  
  int jj = 0;
  int ii = 0;
  background(0);
  translate(width/2, height/2);
  rotateX(theta);
  rotateZ(theta*PI/4);

  for ( int i = -klnMinor/2; i < klnMinor/2; i+=cellSize ){
    for ( int j = -klnMajor/2; j < klnMajor/2; j+=cellSize ){

//        ca.getCell(ii, jj).setAliveColor(150,200,200);
//        stroke(ca.getCell(ii,jj).getAliveColor());                               
//        if(ca.getCell(ii,jj).isAlive()){
//          fill(ca.getCell(ii,jj).getAliveColor());
//        } else {
//          fill(0);
//        }
//        
      //Draw the mobius bands from a bunch of tiny squares
      beginShape(QUAD);
      float r = klnMajor / TWO_PI ;
      float angle = 1/r;
      float phi = TWO_PI/klnMinor;
      
      int iNext = i + cellSize;
      int jNext = j + cellSize;
      
      //first corner
      float x1 = (r  + cos(j/2 * angle)*sin(phi*i) - sin(j/2 * angle)*sin(2*phi*i) )*cos(j * angle);
      float y1 = (r  + cos(j/2 * angle)*sin(phi*i) - sin(j/2 * angle)*sin(2*phi*i) )*sin(j * angle);
      float z1 = sin(j/2 * angle)*sin(phi*i) + cos(j/2 * angle)*sin(2*phi*i);
      
      //second corner
      float x2 = (r  + cos(jNext/2 * angle)*sin(phi*i) - sin(jNext/2 * angle)*sin(2*phi*i) )*cos(jNext * angle);
      float y2 = (r  + cos(jNext/2 * angle)*sin(phi*i) - sin(jNext/2 * angle)*sin(2*phi*i) )*sin(jNext * angle);
      float z2 = sin(jNext/2 * angle)*sin(phi*i) + cos(jNext/2 * angle)*sin(2*phi*i);
      
      //thrid corner
      float x3 = (r  + cos(jNext/2 * angle)*sin(phi*iNext) - sin(jNext/2 * angle)*sin(2*phi*iNext) )*cos(jNext * angle);
      float y3 = (r  + cos(jNext/2 * angle)*sin(phi*iNext) - sin(jNext/2 * angle)*sin(2*phi*iNext) )*sin(jNext * angle);
      float z3 = sin(jNext/2 * angle)*sin(phi*iNext) + cos(jNext/2 * angle)*sin(2*phi*iNext);
      
      //fourth corner
      float x4 = (r  + cos(j/2 * angle)*sin(phi*iNext) - sin(j/2 * angle)*sin(2*phi*iNext) )*cos(j * angle);
      float y4 = (r  + cos(j/2 * angle)*sin(phi*iNext) - sin(j/2 * angle)*sin(2*phi*iNext) )*sin(j * angle);
      float z4 = sin(j/2 * angle)*sin(phi*iNext) + cos(j/2 * angle)*sin(2*phi*iNext);
      
      //plug in the corners
      vertex( x1, y1, z1);
      vertex( x2, y2, z2);
      vertex( x3, y3, z3);
      vertex( x4, y4, z4);
      endShape(); 
      jj++;
      
    }
    jj=0;
    ii++;

  }
  
  if (maybeIterate()){
    //ca.iterate();
    lastRunTime = millis();
  }
  
  theta += 0.01;
}

public boolean maybeIterate() {
  return abs(millis() - lastRunTime) > timeInterval;

}
