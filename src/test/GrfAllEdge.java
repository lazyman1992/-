package test;

import java.awt.color.ICC_ColorSpace;
import java.lang.reflect.Array;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Vector;

import javax.management.loading.PrivateClassLoader;

public class GrfAllEdge {
	
		private int total;
		private String[] nodes;
		private int[][] matirx;
		private ArrayList<Path> paths;
		
		//定义新类，包含路径和路径权重
		public class Path{
			public Stack<Integer> path;
			public int weight;
			
			
			public Path(Stack<Integer> stack,int weight){
				this.path=stack;
				this.weight=weight;
			}
		}
		
		public GrfAllEdge(int total,String[] nodes,ArrayList<Path> list){
			this.total=total;
			this.nodes=nodes;
			this.matirx=new int[total][total];
			//this.paths=new ArrayList<Stack<Integer>>();
			this.paths=list;
		}
		
		
		//打印栈
		private  void printStack(Stack<Integer> stack,int k){
			for(Integer i:stack)
				System.out.print(this.nodes[i]+",");
			System.out.print(this.nodes[k]);
		}
		
		
		//计算路径的权重
		private int countWeight(Stack<Integer> stack,int k){
			Stack<Integer> stack1=(Stack<Integer>) stack.clone();
			stack1.push(k);
			int s=0;
		while(stack1.size()!=1){
			 s=s+this.matirx[stack1.pop()][stack1.peek()];
			}			
		return s ;
		}
		
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
		
		private void addPaths(Path path){
			Stack<Integer> stacknew=(Stack<Integer>)path.path.clone();
			Path temp=new Path(stacknew,path.weight);
			this.paths.add(temp);	
		}
		
		//深度优先搜索寻找所有无环路径
		private void dfsStack(int underTop,int goal,Stack<Integer> stack){
			
			if(stack.isEmpty())
				return ;
			
			int k=stack.peek().intValue();
			int uk=underTop;
			
			if(k==goal){
				System.out.print("\n起点与终点不能相同");
				return ;
			}
			
			//ArrayList<Stack<Integer>> paths=new ArrayList<Stack<Integer>>();
			
			for(int i=0;i<this.total;i++){
				
				if(this.matirx[k][i]>=1&&k!=i){
					
					if(stack.contains(i)){
						if(i!=uk){
							//if(stack.contains(7)&&stack.contains(12)){
//								System.out.print("\n有环:");
//								this.printStack(stack, i);
							//}	
						}
						continue;
					}
					
					if(i==goal){
						if(stack.size()<9){
							
						    System.out.print("\n路径:");
						    this.printStack(stack, i);
						    System.out.print("经过点数："+(stack.size()+1)+" ");
						    System.out.print("路径长度:"+countWeight(stack,i)+"  ");
		                    //printStack1(stack);
					        Path path=new Path(stack, countWeight(stack, i));
						    addPaths(path);
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
		
		//深度优先搜索寻找有环路径
//       private void dfsStackCycle(int underTop,int goal,Stack<Integer> stack){
//			
//			if(stack.isEmpty())
//				return ;
//			
//			int k=stack.peek().intValue();
//			int uk=underTop;
//			
//			if(k==goal){
//				System.out.print("\n起点与终点不能相同");
//				return ;
//			}
//				
//			for(int i=0;i<this.total;i++){
//				
//				if(this.matirx[k][i]>=1&&k!=i){
//					
//					if (stack.contains(i)) {  
//	                    // 由某顶点A，深度访问其邻接点B时，由于是无向图，所以存在B到A的路径，在环路中，我们要排除这种情况  
//	                    // 严格的请，这种情况也是一个环  
//	                    if (i != uk) {  
//                        System.out.print("\n有环:");  
//	                        this.printStack(stack, i);  
//	                    }  
//	                    continue;  
//	                }  
////					
//					
//				
//					if(i==goal){
//						
//						System.out.print("\n路径:");
//						this.printStack(stack, i);
//						
//						continue;
//					}		
//					
//					stack.push(i);
//					dfsStackCycle(k, goal, stack);		
//					
//				}	
//			}		
//			
//			stack.pop();			
//		}
//		
		
		
		//判断无欢路径符合条件的解
		private void searchPaths(ArrayList<Stack<Integer>> paths){
			if(paths==null)
				return ;
			int num=0;
			for(int i=0;i<paths.size();i++){
				//符合所有约束路径
				//printStack(paths.get(i), 17);
				if(paths.get(i).contains(7)&&paths.get(i).contains(12)&&isConnect(paths.get(i), 2, 4)&&isConnect(paths.get(i), 14, 15)){
					System.out.println("---符合所有约束路径---");
					System.out.print("路径：");
					printStack(paths.get(i), 17);
					num++;
				}		
				else if(paths.get(i).contains(7)||paths.get(i).contains(12)||isConnect(paths.get(i), 2, 4)||isConnect(paths.get(i), 14, 15)){
					//System.out.println("---至少符合一条约束路径---");
					//printStack(paths.get(i), 17);
					System.out.print("符合一条约束路径：");
					printStack(paths.get(i), 17);	
					System.out.print("经过点数："+(paths.get(i).size()+1)+" ");
				    System.out.print("路径长度:"+countWeight(paths.get(i),17)+"  ");
				    System.out.println();
					num++;
				}
	
			}
			System.out.println(num);
		
		}
		
		
		private boolean isTwoSameInStack(Stack<Integer> stack){
			if(stack==null)
				return false;
			
			boolean[] map=new boolean[256];
			while(!stack.isEmpty()){
				if(map[stack.peek()]){
					return true;
				}
			    map[stack.pop()]=true;
			}
			
			return false;
			
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
		
		//Dijkstra最小路径
		private  int dijkstraown(int[][] graph,int start,int end){
			if(graph==null)
				return 0;
			replaceZeroToMax(graph);
			
			//初始化d p向量
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
			System.out.print("路径：");
			printArr(p);
			System.out.print("起始点到各点的最小距离：");
			printArr(d);
			return d[end];
		}
		
		
		//打印数组
		private void printArr(int[] arr){
			for(int i=0;i<arr.length;i++)
				System.out.print(arr[i]+" ");
			
			System.out.println();
			
		}
		
		//打印栈
		private void printStack1(Stack<Integer> stack){
			for(Integer i:stack)
				System.out.print(i+" ");
			//System.out.println();
		}
		
		//打印路径数组
		private void printPaths(ArrayList<Stack<Integer>> paths){
			for(int i=0;i<paths.size();i++){
				printStack1(paths.get(i));
			}
			
		}
		
		//打印邻接矩阵
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
		
		
		//初始化邻接矩阵
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
			ArrayList<Path> paths=new ArrayList<Path>();
			GrfAllEdge grf=new GrfAllEdge(18, nodes,paths);
			
			grf.initGrf();
			//grf.printMatrix();
			
			System.out.print("\n-----寻找起点到终点的所有路径开始-----");
			grf.resetVisited();
			int origin=0;
			int goal=17;
			Stack<Integer> stack=new Stack<Integer>();
			
			stack.push(origin);
			grf.dfsStack(-1, goal, stack);
			System.out.println(paths.size());

			//System.out.println(paths.size());
			//grf.printPaths(grf.paths);
			//grf.printStack1(paths.get(1));
			//System.out.println();
			//grf.dijkstraown(grf.matirx,0,17);
			//System.out.println(grf.isConnect(paths.get(2), 2, 4));\
			System.out.println();
			System.out.println("---------------符合约束路径------------------");
			//grf.searchPaths(paths);
			System.out.println(paths.get(0).weight);
		
		}
	
}
