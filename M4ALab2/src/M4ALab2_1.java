public class M4ALab2_1 {

    static double u0(double x){
        return Math.cos(x);
    }

    static double mu(double t){
        return 1-t*t;
    }

    static double N=1, T=1;
    static double h=0.01, tau=0.01;

    public static void main(String[] args) {
        double y[][]=new double[(int)(N/h)+1][(int)(T/tau)+1];
        for(int i=0;i<N/h+1;i++)
            y[0][i]=mu(i*tau);

        for(int i=0;i<T/tau+1;i++)
            y[i][0]=u0(i*h);

        for(int n=0;n<T/tau;n++){
            for(int i=1; i<(int)(N/h); i++)
               y[i][n+1]=(y[i][n]/tau-(y[i][n]-y[i-1][n]-y[i-1][n+1])/2/h)/(1/tau-1/2/h);
        }

        for(int n=0;n<T/tau+1;n++){
            System.out.print(n+"  ");
            for(int i=0; i<N/h+1; i++)
                System.out.print(y[i][n]+"  ");
            System.out.println();
        }

    }
}
