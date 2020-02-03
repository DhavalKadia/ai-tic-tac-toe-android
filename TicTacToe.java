//	Download: http://tictactoe.ai.dhavalkadia.com

package com.dhavalkadia.tictactoe;


public class TicTacToe {

    int[] shell=new int[9];
    int[][] p_win,p_cnt,c_win;
    int c_cnt;
    int z=0,y,alpha=1;
    int[][][] probs=new int[2][362880][9];;
    long w;
    int[][] block;
    int calc=1;

    long [][] swipe=new long [2][362881];
    long combi=0;
    long input=123456789;
    int i=1,j,k,limit,step=1;
    int[] core = new int[20];

    char[][] mat=new char[3][3];

    int testing=1,dwon=0,tie=0;
    int evenfirst=-1;
    int limittemp;

    String curMove=null;
    int playerMove, algoMove;

    boolean needInsert = false, isNew;

    int[][][] probsCopy;

    public TicTacToe()
    {
        init();
        spread(i);

        probsCopy = copyOf3Dim(probs);

    }

    public void refresh()
    {
        z=0;
        alpha=1;
        calc=1;
        combi=0;
        input=123456789;
        i=1;
        step=1;
        testing=1;
        dwon=0;
        tie=0;
        evenfirst=-1;
        mat=new char[3][3];

        mat[0][0]='1';mat[0][1]='2';mat[0][2]='3';
        mat[1][0]='4';mat[1][1]='5';mat[1][2]='6';
        mat[2][0]='7';mat[2][1]='8';mat[2][2]='9';

        probs=new int[2][362880][9];
        probs = copyOf3Dim(probsCopy);
    }

    public int[][][] copyOf3Dim(int[][][] array)
    {
        int[][][] copy;
        copy = new int[array.length][][];
        for (int i = 0; i < array.length; i++) {
            copy[i] = new int[array[i].length][];
            for (int j = 0; j < array[i].length; j++) {
                copy[i][j] = new int[array[i][j].length];
                System.arraycopy(array[i][j], 0, copy[i][j], 0,
                        array[i][j].length);
            }
        }
        return copy;
    }

    public int[][] copyOf2Dim(int[][] array)
    {
        int[][] copy;
        copy = new int[array.length][];


        for (int j = 0; j < array[0].length; j++)
        {
            copy[j] = new int[array[j].length];
            System.arraycopy(array[j], 0, copy[j], 0,
                    array[j].length);
        }

        return copy;
    }

    public int play(int playerMove, int moveCount)
    {

        if(moveCount == 1)
        {
            shell[0]=playerMove;

            add(shell[0], 0);
            clone(9, 1);
            win(9, 0, 3, 1);


            if (shell[0] == 5) {
                int w;
                w = (int) (Math.random()*7177.0);
                w = w % 4;
                if (w == 0)
                    shell[1] = 1;
                else if (w == 1)
                    shell[1] = 3;
                else if (w == 2)
                    shell[1] = 7;
                else if (w == 3)
                    shell[1] = 9;
            } else if (shell[0] == 1 || shell[0] == 3 || shell[0] == 7 || shell[0] == 9) {
                shell[1] = 5;
            } else if (shell[0] == 2 || shell[0] == 4 || shell[0] == 6 || shell[0] == 8) {
                int d = (int) ((Math.random()*7177.0) % 3);
                if (d == 0) {
                    evenfirst = 0;

                    if (shell[0] == 2) {
                        if ((Math.random()*7177.0) % 2 == 0)
                            shell[1] = 1;
                        else
                            shell[1] = 3;
                    } else if (shell[0] == 4) {
                        if ((Math.random()*7177.0) % 2 == 0)
                            shell[1] = 1;
                        else
                            shell[1] = 7;
                    } else if (shell[0] == 6) {
                        if ((Math.random()*7177.0) % 2 == 0)
                            shell[1] = 9;
                        else
                            shell[1] = 3;
                    } else if (shell[0] == 8) {
                        if ((Math.random()*7177.0) % 2 == 0)
                            shell[1] = 7;
                        else
                            shell[1] = 9;
                    }
                } else if (d == 1) {
                    evenfirst = 1;

                    if (shell[0] == 2) {
                        shell[1] = 8;
                    } else if (shell[0] == 4) {
                        shell[1] = 6;
                    } else if (shell[0] == 6) {
                        shell[1] = 4;
                    } else if (shell[0] == 8) {
                        shell[1] = 2;
                    }
                } else
                    shell[1] = 5;
            }

            add(shell[1], 1);
            clone(8, 2);

            return shell[1];
        }

        if(moveCount == 2)
        {
            shell[2]=playerMove;

            add(shell[2], 0);
            clone(7, 3);
            win(7, 0, 5, 3);
            win(7, 0, 4, 2);
            win(7, 0, 3, 1);

            shell[3] = gen(2);

            add(shell[3], 1);
            scan();
            clone(6, 4);

            return shell[3];
        }

        if(moveCount == 3)
        {
            shell[4]=playerMove;

            add(shell[4], 0);
            //#1show();
            clone(5, 5);
            win(5, 0, 5, 3);
            win(5, 0, 4, 2);
            win(5, 0, 3, 1);

            shell[5] = gen(3);
            curMove = "" + shell[5];

            add(shell[5], 1);
            scan();
            clone(4, 6);

            if (calc == 0)
                return 10 + shell[5]; //Algo won
            else
                return shell[5];
        }

        if(moveCount == 4)
        {
            shell[6] = playerMove;

            add(shell[6], 0);
            scan();
            clone(3, 7);
            win(3, 0, 5, 3);
            win(3, 0, 4, 2);

            if(calc==1)
            {
                shell[7] = gen(4);

                if (shell[7] != -1)
                {
                    add(shell[7], 1);
                    curMove = "" + shell[7];

                    if (calc == 1)
                    scan();

                    if (calc == 0)
                        return 10 + shell[7]; //Algo won
                    else
                        return shell[7];
                } else
                    return 20;
            }
            else
                return 21; //Player won
        }

        if(moveCount == 5)
        {
            if (shell[7] != -1)
            {
                shell[8]=playerMove;

                add(shell[8], 0);
                //#1show();
                scan();

                if(calc==1)
                {
                    tie++;

                    return 20;
                }
                else
                    return 21; //Player won (new added)
            }
        }
        return 101;
    }

    void show()
    {
        for(i=0;i<3;i++)
        {
            for(j=0;j<3;j++)
                System.out.print(mat[i][j]+" ");
        }
    }


    void add(int x,int state)
    {
        char c;
        if(state==0)
            c='X';
        else
            c='O';
        {
            if(x%3==1)
            {
                if(x==1)
                    mat[0][0]=c;
                else if(x==4)
                    mat[1][0]=c;
                else if(x==7)
                    mat[2][0]=c;
            }
            else if(x%3==2)
            {
                if(x==2)
                    mat[0][1]=c;
                else if(x==5)
                    mat[1][1]=c;
                else if(x==8)
                    mat[2][1]=c;
            }
            else if(x%3==0)
            {
                if(x==3)
                    mat[0][2]=c;
                else if(x==6)
                    mat[1][2]=c;
                else if(x==9)
                    mat[2][2]=c;
            }
        }

    }

    int shell_check(int x,int l)
    {
        for(int q=0;q<l;q++)
            if(x==shell[q])
                return 0;

        return 1;
    }

    int gen(int step)
    {
        i=0;
        int r=0;
        int flag=0;
        if(step==1)
        {
            int[] ret=new int[40320];
            int v=0;

            for(i=0;i<fact(8);i++)
                if(p_win[i][5]==1)
                    if(p_win[i][0]==shell[0])
                        if(p_win[i][1]!=shell[0])
                        {
                            ret[v]=p_win[i][1];
                            v++;
                        }
            r=(int) (Math.random()*7177.0);
            r=(r<0)?(r*(-1)):r;
            r=(int) (r*7.77777 + v);

            return ret[r%v];

        }
        else if(step==2)
        {
            int solve,c,c2,r2;

            int[] ret=new int[720];
            int v=0;

            for(i=0;i<fact(6);i++)
                if(p_win[i][5]==1)  //Player win on his 3rd move
                    if(p_win[i][0]==shell[0]&&p_win[i][1]==shell[2])
                        if(p_win[i][2]!=shell[1])
                        {
                            flag=1;
                            ret[v]=p_win[i][2];
                            v++;
                        }
            r=(int) (Math.random()*7177.0);
            r=(r<0)?(r*(-1)):r;
            r=(int) (r*7.77777 + v);

            if(flag==1)
                return ret[r%v];
            ret=new int[720];
            v=0;

            for(i=0;i<fact(6);i++)
                if(p_win[i][5]==2)  //Player win on his 4rd move
                    if(p_win[i][0]==shell[0]&&p_win[i][1]==shell[2])
                        if(p_win[i][2]!=shell[1])
                        {
                            flag=1;
                            ret[v]=p_win[i][2];
                            v++;
                        }
            r=(int) (Math.random()*7177.0);
            r=(r<0)?(r*(-1)):r;
            r=(int) (r*7.77777 + v);

            if(flag==1)
                return ret[r%v];

            if(evenfirst==0 && shell[2]%2==0)
            {
                i=0;
                while(i<limittemp)
                {
                    if(c_win[i][4]==1)
                        return c_win[i][1];

                    i++;
                }
            }
            else if(shell[0]==5)
            {
                i=0;
                while(i<limittemp)
                {
                    if(c_win[i][4]==1)
                        return c_win[i][1];

                    i++;
                }
            }
            else if(shell[0]%2==1 && shell[0]!=5)
            {
                if(shell[2]%2==0)
                {
                    c=(shell[0])%3;
                    if(c==0)
                        c=3;

                    c2=(shell[2])%3;
                    if(c2==0)
                        c2=3;

                    r=(shell[0]-1)/3;
                    r2=(shell[2]-1)/3;

                    if(r2==1)
                    {
                        solve=r*3+2;
                    }
                    else
                    {
                        solve=3+c;
                    }


                    if(c!=c2 && r!=r2)
                    {
                        return solve;
                    }

                }
                else if(shell[0]%2==1 && shell[2]%2==1 && shell[0]!=5 && shell[2]!=5)
                {
                    flag=0;v=0;
                    for(i=0;i<fact(6);i++)
                        if(p_win[i][5]==2)
                            if(p_win[i][0]==shell[0]&&p_win[i][1]==shell[2])
                            {
                                flag=1;
                                ret[v]=p_win[i][2];
                                v++;
                            }
                    r=(int) (Math.random()*7177.0);
                    r=(r<0)?(r*(-1)):r;
                    r=(int) (r*7.77777 + v);

                    if(flag==1)
                        return ret[r%v];

                    i=0;
                    while(c_win[i][4]!=1 && (c_win[i][1]%2)==0)
                        i++;
					
                    return c_win[i][1];
                }
            }
            else if(shell[2]%2==1 && shell[2]!=5 && shell[0]%2==0)
            {
                if(evenfirst==1)
                {


                    flag=0;v=0;
                    for(i=0;i<fact(6);i++)
                        if(p_win[i][5]==2)
                            if(p_win[i][0]==shell[0]&&p_win[i][1]==shell[2] && (p_win[i][2]!=5))//c_win[i][1]
                            {
                                flag=1;
                                ret[v]=p_win[i][2];
                                v++;
                            }
                    r=(int) (Math.random()*7177.0);
                    r=(r<0)?(r*(-1)):r;
                    r=(int) (r*7.77777 + v);

                    if(flag==1)
                        return ret[r%v];
                }
				
                i=0;
                while(i<limittemp)
                {
                    if(c_win[i][4]==1)
                        return c_win[i][1];

                    i++;
                }
            }
            else if(shell[0]%2==0 && evenfirst==1 && shell[2]==5)
            {
                if(shell[0] == 2)
                {
                    if((Math.random()*7177.0)%2 == 0)
                        return 1;
                    else
                        return 3;
                }
                else if(shell[0] == 4)
                {
                    if((Math.random()*7177.0)%2 == 0)
                        return 1;
                    else
                        return 7;
                }
                else if(shell[0] == 6)
                {
                    if((Math.random()*7177.0)%2 == 0)
                        return 9;
                    else
                        return 3;
                }
                else if(shell[0] == 8)
                {
                    if((Math.random()*7177.0)%2 == 0)
                        return 7;
                    else
                        return 9;
                }
            }
			
            flag=0;v=0;
            for(i=0;i<fact(6);i++)
                if(p_win[i][5]==2)
                    if(p_win[i][0]==shell[0]&&p_win[i][1]==shell[2])
                    {
                        flag=1;
                        ret[v]=p_win[i][2];
                        v++;
                    }
            r=(int) (Math.random()*7177.0);
            r=(r<0)?(r*(-1)):r;
            r=(int) (r*7.77777 + v);

            if(flag==1)
                return ret[r%v];

            i=0;
            while(c_win[i][4]!=1)
                i++;
			
            return c_win[i][1];

        }
        else if(step==3)
        {

            int[] ret=new int[24];
            int v=0;

            i=0;
            while(i<24)
            {
                if(c_win[i][4]==1)
                    return c_win[i][2];

                i++;
            }

            flag=0;
            for(i=0;i<fact(4);i++)
                if(p_win[i][5]==2)
                    if(p_win[i][0]==shell[0]&&p_win[i][1]==shell[2]&&p_win[i][2]==shell[4])
                    {
                        flag=1;
                        ret[v]=p_win[i][3];
                        v++;
                    }

            r=(int) (Math.random()*7177.0);
            r=(r<0)?(r*(-1)):r;
            r=(int) (r*7.77777 + v);

            if(flag==1)
                return ret[r%v];

            flag=0;v=0;
            for(i=0;i<fact(4);i++)
                if(p_win[i][5]==3)
                    if(p_win[i][0]==shell[0]&&p_win[i][1]==shell[2]&&p_win[i][2]==shell[4])
                        if(shell_check(p_win[i][3],5)==1)
                        {
                            flag=1;
                            ret[v]=p_win[i][3];
                            v++;
                        }

            r=(int) (Math.random()*7177.0);
            r=(r<0)?(r*(-1)):r;
            r=(int) (r*7.77777 + v);

            if(flag==1)
                return ret[r%v];
        }
        else if(step==4)
        {
            i=0;
            while(i<2)
            {
                if(c_win[i][4]==2)
                    return c_win[i][3];
                i++;
            }

            for(i=0;i<2;i++)
                if(p_win[i][5]==3)
                    if(p_win[i][0]==shell[0]&&p_win[i][1]==shell[2]&&p_win[i][2]==shell[4]&&p_win[i][3]==shell[6])
                        return p_win[i][4];

        }
        return -1; //java
    }

    void win(int x,int mode,int level,int mark)
    {     int t=(mode==0)?5:4;
        int m,n;
        {
            for(i=0;i<fact(x-1);i++)
            {
                for(m=0;m<3;m++)
                    for(n=0;n<3;n++)
                        block[m][n]=0;

                for(k=0;k<level;k++)
                {
                    if(p_win[i][k]%3==1)
                    {
                        if(p_win[i][k]==1)
                            block[0][0]=1;
                        else if(p_win[i][k]==4)
                            block[1][0]=1;
                        else if(p_win[i][k]==7)
                            block[2][0]=1;
                    }
                    else if(p_win[i][k]%3==2)
                    {
                        if(p_win[i][k]==2)
                            block[0][1]=1;
                        else if(p_win[i][k]==5)
                            block[1][1]=1;
                        else if(p_win[i][k]==8)
                            block[2][1]=1;
                    }
                    else if(p_win[i][k]%3==0)
                    {
                        if(p_win[i][k]==3)
                            block[0][2]=1;
                        else if(p_win[i][k]==6)
                            block[1][2]=1;
                        else if(p_win[i][k]==9)
                            block[2][2]=1;
                    }
                }
                if(analyze(block)==1)
                    p_win[i][5]=mark;

            }

            if(mark<3)
                for(i=0;i<fact(x-1);i++)
                {
                    limittemp=fact(x-1);

                    for(m=0;m<3;m++)
                        for(n=0;n<3;n++)
                            block[m][n]=0;

                    for(k=0;k<level;k++)
                    {
                        if(c_win[i][k]%3==1)
                        {
                            if(c_win[i][k]==1)
                                block[0][0]=1;
                            else if(c_win[i][k]==4)
                                block[1][0]=1;
                            else if(c_win[i][k]==7)
                                block[2][0]=1;
                        }
                        else if(c_win[i][k]%3==2)
                        {
                            if(c_win[i][k]==2)
                                block[0][1]=1;
                            else if(c_win[i][k]==5)
                                block[1][1]=1;
                            else if(c_win[i][k]==8)
                                block[2][1]=1;
                        }
                        else if(c_win[i][k]%3==0)
                        {
                            if(c_win[i][k]==3)
                                block[0][2]=1;
                            else if(c_win[i][k]==6)
                                block[1][2]=1;
                            else if(c_win[i][k]==9)
                                block[2][2]=1;
                        }
                    }
                    if(analyze(block)==1)
                        c_win[i][4]=mark;

                }
        }
    }

    void scan()// throws IOException
    {
        if(((mat[0][0]==mat[0][1])&&(mat[0][1]==mat[0][2])&&(mat[0][2]=='X')) | ((mat[1][0]==mat[1][1])&&(mat[1][1]==mat[1][2])&&(mat[1][2]=='X')) | ((mat[2][0]==mat[2][1])&&(mat[2][1]==mat[2][2])&&(mat[2][2]=='X')) |
                ((mat[0][0]==mat[1][0])&&(mat[1][0]==mat[2][0])&&(mat[2][0]=='X')) | ((mat[0][1]==mat[1][1])&&(mat[1][1]==mat[2][1])&&(mat[2][1]=='X')) | ((mat[0][2]==mat[1][2])&&(mat[1][2]==mat[2][2])&&(mat[2][2]=='X')) |
                ((mat[0][0]==mat[1][1])&&(mat[1][1]==mat[2][2])&&(mat[2][2]=='X')) | ((mat[0][2]==mat[1][1])&&(mat[1][1]==mat[2][0])&&(mat[2][0]=='X'))                                      )
        {
            calc=0;
            testing=0;
        }
        else if(((mat[0][0]==mat[0][1])&&(mat[0][1]==mat[0][2])&&(mat[0][2]=='O')) | ((mat[1][0]==mat[1][1])&&(mat[1][1]==mat[1][2])&&(mat[1][2]=='O')) | ((mat[2][0]==mat[2][1])&&(mat[2][1]==mat[2][2])&&(mat[2][2]=='O')) |
                ((mat[0][0]==mat[1][0])&&(mat[1][0]==mat[2][0])&&(mat[2][0]=='O')) | ((mat[0][1]==mat[1][1])&&(mat[1][1]==mat[2][1])&&(mat[2][1]=='O')) | ((mat[0][2]==mat[1][2])&&(mat[1][2]==mat[2][2])&&(mat[2][2]=='O')) |
                ((mat[0][0]==mat[1][1])&&(mat[1][1]==mat[2][2])&&(mat[2][2]=='O')) | ((mat[0][2]==mat[1][1])&&(mat[1][1]==mat[2][0])&&(mat[2][0]=='O'))                                      )
        {

            calc=0;
            dwon++;
        }
        else if(calc!=1)
        {
            tie++;
        }
    }


    int analyze(int m[][])
    {
        if(((m[0][0]==m[0][1])&&(m[0][1]==m[0][2])&&(m[0][2]==1)) | ((m[1][0]==m[1][1])&&(m[1][1]==m[1][2])&&(m[1][2]==1)) | ((m[2][0]==m[2][1])&&(m[2][1]==m[2][2])&&(m[2][2]==1)) |
                ((m[0][0]==m[1][0])&&(m[1][0]==m[2][0])&&(m[2][0]==1)) | ((m[0][1]==m[1][1])&&(m[1][1]==m[2][1])&&(m[2][1]==1)) | ((m[0][2]==m[1][2])&&(m[1][2]==m[2][2])&&(m[2][2]==1)) |
                ((m[0][0]==m[1][1])&&(m[1][1]==m[2][2])&&(m[2][2]==1)) | ((m[0][2]==m[1][1])&&(m[1][1]==m[2][0])&&(m[2][0]==1))                                      )
            return 1;
        else
            return 0;
    }

    void temp_win(int x)
    {
        for(i=0;i<fact(x-1);i++)
        {
            p_win[i][0]=probs[x%2][i][0]; p_win[i][1]=probs[x%2][i][2];
            p_win[i][2]=probs[x%2][i][4]; p_win[i][3]=probs[x%2][i][6]; p_win[i][4]=probs[x%2][i][8];

            c_win[i][0]=probs[x%2][i][1]; c_win[i][1]=probs[x%2][i][3];
            c_win[i][2]=probs[x%2][i][5]; c_win[i][3]=probs[x%2][i][7];
        }
    }


    void clone(int x,int state)
    {
        alloc(x);

        int u=0;
        for(i=0;i<w;i++)
        {

            if(shell[0]==probs[(x-1)%2][i][0])
                if(state==1)
                {
                    for(j=0;j<9;j++)
                        probs[x%2][u][j]=probs[(x-1)%2][i][j];
                    u++;
                }
                else
                {
                    if(shell[1]==probs[(x-1)%2][i][1])
                        if(state==2)
                        {
                            for(j=0;j<9;j++)
                                probs[x%2][u][j]=probs[(x-1)%2][i][j];
                            u++;
                        }
                        else
                        {
                            if(shell[2]==probs[(x-1)%2][i][2])
                                if(state==3)
                                {
                                    for(j=0;j<9;j++)
                                        probs[x%2][u][j]=probs[(x-1)%2][i][j];
                                    u++;
                                }
                                else
                                {
                                    if(shell[3]==probs[(x-1)%2][i][3])
                                        if(state==4)
                                        {
                                            for(j=0;j<9;j++)
                                                probs[x%2][u][j]=probs[(x-1)%2][i][j];
                                            u++;
                                        }
                                        else
                                        {
                                            if(shell[4]==probs[(x-1)%2][i][4])
                                                if(state==5)
                                                {
                                                    for(j=0;j<9;j++)
                                                        probs[x%2][u][j]=probs[(x-1)%2][i][j];
                                                    u++;
                                                }
                                                else
                                                {
                                                    if(shell[5]==probs[(x-1)%2][i][5])
                                                        if(state==6)
                                                        {
                                                            for(j=0;j<9;j++)
                                                                probs[x%2][u][j]=probs[(x-1)%2][i][j];
                                                            u++;
                                                        }
                                                        else
                                                        {
                                                            if(shell[6]==probs[(x-1)%2][i][6])
                                                                if(state==7)
                                                                {
                                                                    for(j=0;j<9;j++)
                                                                        probs[x%2][u][j]=probs[(x-1)%2][i][j];
                                                                    u++;
                                                                }
                                                                else
                                                                {
                                                                    if(shell[7]==probs[(x-1)%2][i][7])
                                                                        if(state==8)
                                                                        {
                                                                            for(j=0;j<9;j++)
                                                                                probs[x%2][u][j]=probs[(x-1)%2][i][j];
                                                                            u++;
                                                                        }
                                                                }
                                                        }
                                                }
                                        }
                                }
                        }
                }
        }
        temp_win(x);
    }

    void alloc(int l)
    {
        w=fact(l);

        probs[l%2]=new int[(int) w][9];

        p_win=new int[fact(l-1)][6];

        c_win=new int[fact(l-1)][6];
    }

    int fact(int x)
    {
        if(x>0)
            return x*fact(x-1);
        else
            return 1;
    }

    void spread(int i)
    {
        int invert=1;

        if(i==1)
            invert=0;

        if(step<limit)
        {
            int t=0;
            for(j=0;j<limit;j++)
            {
                for(k=0;swipe[i][k]!=-1;k++)
                {
                    if(swipe[i][k]>0&&check(swipe[i][k],core[j])==1)
                    {
                        swipe[invert][t]=swipe[i][k]*10+core[j] ;
                        if(swipe[invert][t]>100000000)
                        {
                            for(y=0;y<9;y++)
                            {
                                probs[0][z][y]=(int) (swipe[invert][t]%10);
                                swipe[invert][t]/=10;
                            }
                            z++;
                        }
                        combi++;
                        t++;
                    }
                }
            }
            swipe[invert][t]=-1;
            step++;
            spread(invert);
        }
    }

    void init()
    {
        int flag=1;
        mat[0][0]='1';mat[0][1]='2';mat[0][2]='3';
        mat[1][0]='4';mat[1][1]='5';mat[1][2]='6';
        mat[2][0]='7';mat[2][1]='8';mat[2][2]='9';

        j=0;
        int t=1;
        while(input>0)
        {
            core[j]=(int) (input%10);

            for(int k=0;k<j;k++)
                if(core[k]==input%10)
                {
                    flag=0;
                    break;
                }

            if(flag==1)
            {
                swipe[1][j]=core[j];

                j++;
                limit=j;
            }
            flag=1;
            input/=10;
        }
        swipe[1][j]=-1;

        block=new int[3][3];


    }

    int check(long swipe2,int in)
    {
        int out=1;

        while(swipe2>0)
        {
            if(in==swipe2%10)
            {
                out=0;
                break;
            }
            swipe2/=10;
        }
        return out;
    }
}