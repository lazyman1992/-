package test;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;



public class GrfAllEdge {
	
		private int total;
		private int[][] matirx;
		private ArrayList<Path> paths;
		
		//�������࣬����·����·��Ȩ��
		public class Path{
			public Stack<Integer> path;//�������յ�
			public int weight;	
			public Path(Stack<Integer> stack,int weight){
				this.path=stack;
				this.weight=weight;
			}
		}
		
		public GrfAllEdge(int total,ArrayList<Path> list){
			this.total=total;
//			this.nodes=nodes;
			this.matirx=new int[total][total];
			//this.paths=new ArrayList<Stack<Integer>>();
			this.paths=list;
		}
		
		
		//��ӡջ
		private  void printStack(Stack<Integer> stack){
			for(Integer i:stack)
				System.out.print(i+",");
	//		System.out.print(k);
		}
		
		
		//��ӡ����
				private void printArr(int[] arr){
					for(int i=0;i<arr.length;i++)
						System.out.print(arr[i]+" ");
					
					System.out.println();
					
				}
				
				//��ӡջ
				private void printStack1(Stack<Integer> stack){
					for(Integer i:stack)
						System.out.print(i+" ");
					//System.out.println();
				}
				
				//��ӡ·������
				private void printPaths(ArrayList<Stack<Integer>> paths){
					for(int i=0;i<paths.size();i++){
						printStack1(paths.get(i));
					}
					
				}
				
				//��ӡ�ڽӾ���
				private void printMatrix(){
					System.out.println("----------matrix----------");
//					System.out.println("---0-1-2-3-4-5-6-7-8-9-10-11-12-13-14-15-16-17---");
//					System.out.println("---A-B-C-D-E-F-G-H-I-J-K-L-M-N-O-P-Q-R---");
					for(int i=0;i<this.total;i++){
						//System.out.print(" "+this.nodes[i]+"|");
						for(int j=0;j<this.total;j++){
							System.out.print(this.matirx[i][j]+"-");
						}
						System.out.print("\n\n");
					}
					System.out.println("----------matrix----------");
				}
				
				
				private void resetVisited(){
					for(int i=0;i<this.total;i++){
						this.matirx[i][i]=0;
					}
				}
				
		
		//����·����Ȩ��
//		private int countWeight(Stack<Integer> stack){
//			Stack<Integer> stack1=(Stack<Integer>) stack.clone();
////			stack1.push(k);
//			int s=0;
//		while(stack1.size()!=1){
//			 s=s+this.matirx[stack1.pop()][stack1.peek()];
//			}			
//		return s ;
//		}
		
		private int countWeight(Stack<Integer> stack,int k){
			Stack<Integer> stack1=(Stack<Integer>) stack.clone();
		stack1.push(k);
			int s=0;
		while(stack1.size()!=1){
			 s=s+this.matirx[stack1.pop()][stack1.peek()];
			}			
		return s ;
		}
		
		//�ж������Ƿ�����
		private boolean isConnect(Stack<Integer>stack,int i,int j){
			if(stack==null)
				return false;
			Stack<Integer> stacktemp=(Stack<Integer>)stack.clone();
			while(!stacktemp.isEmpty()){
				int temp=stacktemp.pop();
				if(temp==i||temp==j){
					if(stacktemp.peek()==i||stacktemp.peek()==j)
						return true;
				}
			}	
			return false;
		}
		
		
		//��Path�����path
		private void addPaths(Path path){
			Stack<Integer> stacknew=(Stack<Integer>)path.path.clone();
			Path temp=new Path(stacknew,path.weight);
			this.paths.add(temp);	
		}
		
		
		//�����������Ѱ�������޻�·��
		private void dfsStack(int underTop,int goal,Stack<Integer> stack){
			
			if(stack.isEmpty())
				return ;
			
			int k=stack.peek().intValue();
			int uk=underTop;
			
			if(k==goal){
				System.out.print("\n������յ㲻����ͬ");
				return ;
			}
				
			for(int i=0;i<this.total;i++){
				
				if(this.matirx[k][i]>=1&&k!=i){
					
					if(stack.contains(i)){
						
						continue;
					}
					
					if(i==goal){
						//if(stack.contains(7)&&stack.contains(12)&&isConnect(stack, 2, 4)&&isConnect(stack, 13, 14)){
							
//						    System.out.print("\n·��:");
//						    System.out.print("����������"+(stack.size()+1)+" ");
//						    System.out.print("·������:"+countWeight(stack,i)+"  ");
//		                    this.printStack(stack,i);
							//stack.push(i);
					        Path path=new Path(stack, countWeight(stack,i));
						    addPaths(path);
					//}
					
						continue;
					}		
					
					stack.push(i);
					dfsStack(k, goal, stack);		
					
				}	
			}		
			
			stack.pop();	
				
		}
		
		//Ѱ��·�������е���С·��
		private   ArrayList<Path> findMinPath(ArrayList<Path> paths,int end){
			if(paths==null)
				return null;
			ArrayList<Path> minPaths=new ArrayList<Path>();
			int n=paths.size();
			int mincost=paths.get(0).weight;
			for(int i=0;i<n;i++){
				if(mincost>paths.get(i).weight)
					mincost=paths.get(i).weight;
				
			}
			
			for(int i=0;i<n;i++){
				if(paths.get(i).weight==mincost){
					minPaths.add(paths.get(i));
					System.out.println("---���·��---");
				System.out.print("����������"+(paths.get(i).path.size()+1)+" ");
			    System.out.print("·������:"+countWeight(paths.get(i).path,end)+"  ");
			    System.out.println();
                this.printStack(paths.get(i).path);
                }
			}
			
			return minPaths;
		}
		
		//�ж�·���Ƿ��л�
//		private boolean isCycle(Stack<Integer> stack,int n,int i){
//			Stack<Integer> stacknew=(Stack<Integer>)stack.clone();
//			if(stack.pop()==n&&stack.peek()==i)
//				return true;
//			else
//				return false;
//		}
			
		
		
		//�ж��޻�·�����������Ľ�
		private void searchPaths(ArrayList<Path> paths,int n1,int n2,int b1,int b2,int b3,int b4){
			if(paths==null)
				return ;
			int num=0;
			
			for(int i=0;i<paths.size();i++){
				
			
				
				
	
			}
			System.out.println(num);
		
		}
		
		
		
		
		private  void replaceZeroToMax(int[][] graph){
			if(graph==null)
				return ;
			int r=graph.length;
			int c=graph[0].length;
			for(int i=0;i<r;i++){
				for(int j=0;j<c;j++){
					if(graph[i][j]==0){
						if(i==j)
							graph[i][j]=0;
						else
						    graph[i][j]=Integer.MAX_VALUE;
						}
				}
			}		
		}
		
		//Dijkstra��С·��
		private  int dijkstraown(int[][] graph,int start,int end){
			if(graph==null)
				return 0;
			replaceZeroToMax(graph);
			
			//��ʼ��d p����
			int n=graph[0].length;
			int[] d=new int[n];
			int[] p=new int[n];
			boolean[] v=new boolean[n];
			for(int i=0;i<=n-1;i++){
				d[i]=graph[start][i];
				v[i]=false;
				p[i]=0;
			}	
			
			v[start]=true;
			int min,k=0;
			
			for(int i=0;i<n;i++){
				 min=Integer.MAX_VALUE;k=0;
				for(int j=0;j<n;j++){
					if(!v[j]&&d[j]<min){
						min=d[j];
						k=j;
					}			
				}
				v[k]=true;
				for(int j=0;j<n;j++){
					int tmp=(graph[k][j]==Integer.MAX_VALUE?Integer.MAX_VALUE:(min+graph[k][j]));
					if(!v[j]&&tmp<d[j]){
						d[j]=min+graph[k][j];
						p[j]=k;
					}		
				}
			
			}
			//System.out.print("·����");
			//printArr(p);
			//System.out.print("��ʼ�㵽�������С���룺");
			//printArr(d);
			return d[end];
		}
		
		
		//�л��ж�����
		private int isCycle(int[][] graph,int n1,int n2,int b1,int b2,int b3,int b4){
			int n=graph.length;
			int d=dijkstraown(graph, n1, n2);
			boolean flag=false;
			if(((n1==b1)&&(n2==b2))||((n1==b2)&&(n2==b1))||((n1==b3)&&(n2==b4))||((n1==b3)&&(n2==b4)))
				flag=false;
			else
				flag=true;
			for(int k=0;k<n;k++){
				if(graph[n1][k]>0&&graph[n2][k]>0&&(d==graph[n1][k]+graph[k][n2])&&flag)
					return k;
			}
			
			return 0;
		}
		
		
		//��ʼ���ڽӾ���
		private void initGrf(int[][] graph){	
			
			
			int pointnum=graph[0][0];
			System.out.println(pointnum);
			for(int i=0;i<pointnum;i++)
				for(int j=0;j<pointnum;j++)
					this.matirx[i][j]=0;
			
			int edgenum=graph[0][1];
			for(int i=1;i<=edgenum;i++)
				this.matirx[graph[i][1]][graph[i][2]]=this.matirx[graph[i][2]][graph[i][1]]=graph[i][3];			
		}
		
		//�ַ���ת��Ϊ����
		public static int[] converStringToInt(String str){
			if(str==null)
				return null;
			
			int[] s=new int[4];
			char[] chas=str.toCharArray();
			int num=0,res=0,cur=0;
			for(int i=0;i<chas.length;i++){
				cur=chas[i]-'0';
				if(cur<0||cur>9){
					//System.out.println(res);
					s[num++]=res;
					res=0;				
				}
				else
				res=cur+res*10;			
			}
			s[num++]=res;
			return s;	
		}
		
		public static void main(String[] args){
				
			long startTime=System.currentTimeMillis();
			
			//��ȡ�ļ�
			File csv = new File("C://Users//shen//Desktop//����//topo2.csv");  // CSV�ļ�·��
		    BufferedReader br = null;
		    try
		    {
		        br = new BufferedReader(new FileReader(csv));
		    } catch (FileNotFoundException e)
		    {
		        e.printStackTrace();
		    }
		    String line = "";
		    String everyLine = "";
		    int obj[][] = new int[5000][]; 
		    try {
		            List<String> allString = new ArrayList<>();
		            while ((line = br.readLine()) != null)  //��ȡ�������ݸ�line����
		            {
		                everyLine = line;
		                //System.out.println(everyLine);
		                allString.add(everyLine);
		            }
		            int size = allString.size(); 
		         
		            if (size == 0) { 
		                //log.info("����Ϊ��,ת������ʧ�ܣ�������null��"); 
		               // return null; 
		            	System.out.println("����Ϊ��");
		            } 
		            for (int i = 0; i < size; i++) { 
		                obj[i] =converStringToInt(allString.get(i)); 
		            } 	            
		            //System.out.println("csv���������������"+allString.size());
		    } catch (IOException e)
		    {
		        e.printStackTrace();
		    }
		   
		    ArrayList<Path> paths=new ArrayList<Path>();	//�洢���з�������·��
		    ArrayList<Path> minpaths=new ArrayList<Path>();//�洢���·��
		    int origin=0;
			int goal=obj[0][0];
		    GrfAllEdge grf=new GrfAllEdge(goal, paths);
			
			grf.initGrf(obj);
	    	//grf.printMatrix();
			
			
			
			System.out.print("\n-----Ѱ����㵽�յ������·����ʼ-----");
			
			Stack<Integer> stack=new Stack<Integer>();		
			stack.push(origin);
			grf.dfsStack(-1, goal-1, stack);
			System.out.println();
			System.out.println("·��������"+paths.size());
			minpaths=grf.findMinPath(paths,goal-1);
			//printPaths(minpaths);

			//System.out.println(paths.size());
			//grf.printPaths(grf.paths);
			//grf.printStack1(paths.get(1));
			//System.out.println();
			//grf.dijkstraown(grf.matirx,0,17);
			//System.out.println(grf.isConnect(paths.get(2), 2, 4));\
//			System.out.println();
//			System.out.println("---------------����Լ��·��------------------");
//			grf.searchPaths(paths);
			//System.out.println(paths.get(0).weight);
			
			
			long endTime=System.currentTimeMillis();
			System.out.println();
			System.out.print("��������ʱ�䣺"+(endTime-startTime)+"ms");
			
			
			
		
		}
	
}
