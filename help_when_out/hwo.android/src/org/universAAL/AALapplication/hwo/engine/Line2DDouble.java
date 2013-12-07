package org.universAAL.AALapplication.hwo.engine;

// Geometry class to be used at the Wandering detector. This class has to be put in the servlet.

public class Line2DDouble{

        public double x1;
        public double y1;
        public double x2;
        public double y2;

        public Line2DDouble() {
        }

        public Line2DDouble(double x1, double y1, double x2, double y2) {
            setLine(x1, y1, x2, y2);
        }

            
        public double getX1() {
            return x1;
        }

       
        public double getY1() {
            return y1;
        }

        
        public double getX2() {
            return x2;
        }

      
        public double getY2() {
            return y2;
        }

       
        public void setLine(double x1, double y1, double x2, double y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }

        public static boolean linesIntersect(double x1, double y1, double x2,
                double y2, double x3, double y3, double x4, double y4)
        {
            

            x2 -= x1; // A
            y2 -= y1;
            x3 -= x1; // B
            y3 -= y1;
            x4 -= x1; // C
            y4 -= y1;

            double AvB = x2 * y3 - x3 * y2;
            double AvC = x2 * y4 - x4 * y2;

            // Online
            if (AvB == 0.0 && AvC == 0.0) {
                if (x2 != 0.0) {
                    return
                        (x4 * x3 <= 0.0) ||
                        ((x3 * x2 >= 0.0) &&
                         (x2 > 0.0 ? x3 <= x2 || x4 <= x2 : x3 >= x2 || x4 >= x2));
                }
                if (y2 != 0.0) {
                    return
                        (y4 * y3 <= 0.0) ||
                        ((y3 * y2 >= 0.0) &&
                         (y2 > 0.0 ? y3 <= y2 || y4 <= y2 : y3 >= y2 || y4 >= y2));
                }
                return false;
            }

            double BvC = x3 * y4 - x4 * y3;

            return (AvB * AvC <= 0.0) && (BvC * (AvB + BvC - AvC) <= 0.0);
        }
        
        
        
        public boolean intersectsLine(Line2DDouble l) {
            return linesIntersect(l.getX1(), l.getY1(), l.getX2(), l.getY2(), getX1(), getY1(), getX2(), getY2());
        }
    }