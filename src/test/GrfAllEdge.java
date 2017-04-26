package test;

import java.lang.reflect.Array;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Vector;

public class GrfAllEdge {
	
		private int total;
		private String[] nodes;
		private int[][] matirx;
		private ArrayList<Stack<Integer>> paths;
		
		public GrfAllEdge(int total,String[] nodes){
			this.total=total;
			this.nodes=nodes;
			this.matirx=new int[total][total];
			this.paths=new ArrayList<Stack<Integer>>();
		}
		
		
		//��ӡջ
		private  void printStack(Stack<Integer> stack,int k){
			for(Integer i:stack)
				System.out.print(this.nodes[i]+",");
			System.out.print(this.nodes[k]+",");
		}
		
		
		//����·����Ȩ��
		private int countWeight(Stack<Integer> stack,int k){
			Stack<Integer> stack1=(Stack<Integer>) stack.clone();
			stack1.push(k);
			int s=0;
		while(stack1.size()!=1){
			 s=s+this.matirx[stack1.pop()][stack1.peek()];
			}			
		return s ;
		}
		
		private void addPaths(ArrayList<Stack<Integer>>paths,Stack<Integer> stack){
			Stack<Integer> stacknew=(Stack<Integer>)stack.clone();
			this.paths.add(stacknew);	
		}
		
		//�����������Ѱ������·��
		private void dfsStack(int underTop,int goal,Stack<Integer> stack){
			
			if(stack.isEmpty())
				return ;
			
			int k=stack.peek().intValue();
			int uk=underTop;
			
			if(k==goal){
				System.out.print("\n������յ㲻����ͬ");
				return ;
			}
			
			//ArrayList<Stack<Integer>> paths=new ArrayList<Stack<Integer>>();
			
			for(int i=0;i<this.total;i++){
				
				if(this.matirx[k][i]>=1&&k!=i){
					if(stack.contains(i)){
						if(i!=uk){
							//if(stack.contains(7)&&stack.contains(12)){
//								System.out.print("\n�л�:");
//								this.printStack(stack, i);
							//}
							
						}
						continue;
					}
					
					if(i==goal){
						
						if(stack.size()<9/*&&stack.contains(7)&&stack.contains(12)&&stack.contains(2)&&stack.contains(4)*/){
							
						System.out.print("\n·��:");
						this.printStack(stack, i);
						System.out.print("����������"+(stack.size()+1)+" ");
						System.out.print("·������:"+countWeight(stack,i)+"  ");
		               // printStack1(stack);
		               
					   addPaths(paths,stack);
						//printStack1(this.paths.get(1));
						}
						continue;
					}		
						
					stack.push(i);
					dfsStack(k, goal, stack);		
					
				}	
			}		
			
			stack.pop();	
			
			
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
					//System.out.print(graph[i][j]+" ");
				}
				//System.out.println();
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
			
			for(int i=1;i<n;i++){
				 min=Integer.MAX_VALUE;k=0;
				for(int j=1;j<n;j++){
					if(!v[j]&&d[j]<min){
						min=d[j];
						k=j;
					}			
				}
				v[k]=true;
				for(int j=1;j<n;j++){
					int tmp=(graph[k][j]==Integer.MAX_VALUE?Integer.MAX_VALUE:(min+graph[k][j]));
					if(!v[j]&&tmp<d[j]){
						d[j]=min+graph[k][j];
						p[j]=k;
					}		
				}
			
			}
			System.out.print("·����");
			printArr(p);
			System.out.print("��ʼ�㵽�������С���룺");
			printArr(d);
			return d[end];
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
			System.out.println("---0-1-2-3-4-5-6-7-8-9-10-11-12-13-14-15-16-17---");
			System.out.println("---A-B-C-D-E-F-G-H-I-J-K-L-M-N-O-P-Q-R---");
			for(int i=0;i<this.total;i++){
				System.out.print(" "+this.nodes[i]+"|");
				for(int j=0;j<this.total;j++){
					System.out.print(this.matirx[i][j]+"-");
				}
				System.out.print("\n");
			}
			System.out.println("----------matrix----------");
		}
		
		
		private void resetVisited(){
			for(int i=0;i<this.total;i++){
				this.matirx[i][i]=0;
			}
		}
		
		
		//��ʼ���ڽӾ���
		private void initGrf(){
			int[][]matirx1={ 	{0,3,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
	                {3,0,1,0,1,0,0,0,0,4,0,0,0,0,0,0,0,0},
	                {1,1,0,1,2,1,0,0,0,0,0,0,0,0,0,0,0,0},
	                {1,0,1,0,0,2,2,1,0,0,0,0,0,0,0,0,0,0},
	                {0,1,2,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0},
	                {0,0,1,2,1,0,1,0,0,3,1,0,3,0,0,0,0,0},
	                {0,0,0,2,0,1,0,1,2,0,0,0,2,4,3,0,0,0},
	                {0,0,0,1,0,0,1,0,1,0,0,0,0,0,0,0,0,0},
	                {0,0,0,0,0,0,2,1,0,0,0,0,0,0,1,3,0,0},
	                {0,4,0,0,1,3,0,0,0,0,1,1,0,0,0,0,0,0},
	                {0,0,0,0,0,1,0,0,0,1,0,1,2,0,0,0,0,0},
	                {0,0,0,0,0,0,0,0,0,1,1,0,1,0,0,0,1,0},
	                {0,0,0,0,0,3,2,0,0,0,2,1,0,2,0,0,1,0},
	                {0,0,0,0,0,0,4,0,0,0,0,0,2,0,1,2,2,1},
	                {0,0,0,0,0,0,3,0,1,0,0,0,0,1,0,1,0,0},
	                {0,0,0,0,0,0,0,0,3,0,0,0,0,2,1,0,0,4},
	                {0,0,0,0,0,0,0,0,0,0,0,1,1,2,0,0,0,1},
	                {0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,4,1,0}};
			
			for(int i=0;i<18;i++)
				for(int j=0;j<18;j++)
					this.matirx[i][j]=matirx1[i][j];			
			
		}
		
		public static void main(String[] args){
			String[] nodes=new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R"};
			GrfAllEdge grf=new GrfAllEdge(18, nodes);
			
			grf.initGrf();
			//grf.printMatrix();
			
			System.out.print("\n-----Ѱ����㵽�յ������·����ʼ-----");
			grf.resetVisited();
			int origin=0;
			int goal=17;
			Stack<Integer> stack=new Stack<Integer>();
			ArrayList<Stack<Integer>> paths=new ArrayList<Stack<Integer>>();
			stack.push(origin);
			grf.dfsStack(-1, goal, stack);
			//System.out.println();
			//System.out.println(grf.paths.size());
			//grf.printPaths(grf.paths);
			//grf.printStack1(paths.get(0));
			//System.out.println();
			//grf.dijkstraown(grf.matirx,0,17);
			
		
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	

}
