


public class M4ALab2_1 {

    double u0(double x){
        return Math.cos(x);
    }

    double mu(double t){
        return 1-t*t;
    }

    static double N=1, T=1;
    static double h=0.01, tau=0.01;

    public static void main(String[] args) {
        double y[][]=new double[(int)(N/h)][(int)(T/tau)];
        for(int i=1;i<N/h;i++){                        // 0.01<=x<=0.99
            y[i][1]=
        }
    }
}
