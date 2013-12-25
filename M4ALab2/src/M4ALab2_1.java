public class M4ALab2_1 {

    static double u0(double x){
        return Math.cos(x);
    }

    static double mu(double t){
        return 1-t*t;
    }

    static double ux0(double x){
        return 3*x*x*x+x+1;
    }

    static double u1t(double t){
        return 5+t;
    }

    static double du0t(double t){
        return t+1;
    }

    static double a(double x){
        return 5*x;
    }

    static double fi(double x, double t){
        return 90*x-t*t-1;
    }

    static double N=1, T=1;
    static double h=0.01, tau=0.02;

    public static void main(String[] args) {
//        Task1
        double y[][]=new double[(int)(N/h)+1][(int)(T/tau)+1];
        for(int i=0;i<T/tau+1;i++)
            y[0][i]=mu(i*tau);

        for(int i=0;i<N/h+1;i++)
            y[i][0]=u0(i*h);

        for(int n=0;n<T/tau;n++){
            for(int i=1; i<(int)(N/h); i++)
               y[i][n+1]=(y[i][n]/tau-(y[i][n]-y[i-1][n]-y[i-1][n+1])/2/h)/(1/tau-1/2/h);
        }

        for(int n=0;n<T/tau+1;n++){
            for(int i=0; i<N/h+1; i++)
                System.out.print(y[i][n]+"\t");
            System.out.println();
        }

//        //Task2
//        h=0.1;
//        tau=0.001;
//        N=1;
//        T=0.5;
//        double y[][]=new double[(int)(N/h)][(int)(T/tau)];
//        for(int i=0;i<T/tau;i++)
//            y[(int)(N/h)-1][i]=u1t(i*tau);
//
//        for(int i=0;i<N/h;i++)
//            y[i][0]=ux0(i*h);
//
//        for(int n=0;n<T/tau-1;n++){
//            for(int i=(int)(N/h)-2; i>0; i--)
//                y[i][n+1]=(tau*a(i*h)*(y[i+1][n]-2*y[i][n]+y[i-1][n])/h/h+y[i][n]-tau*fi(i*h, n*tau));
//            y[0][n]=y[1][n]-du0t(n*tau)*h;
//        }
//
//        for(int n=0;n<T/tau;n++){
//            for(int i=0; i<N/h; i++)
//                System.out.print(y[i][n]+"\t");
//            System.out.println();
//        }

    }
}
