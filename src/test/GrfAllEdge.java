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
		
		//定义新类，包含路径和路径权重
		public class Path{
			public Stack<Integer> path;//不包含终点
			public int weight;	
			public Path(Stack<Integer> stack,int weight){
				this.path=stack;
				this.weight=weight;
			}
		}
		
		public GrfAllEdge(int total,ArrayList<Path> list){
			this.total=total;
			this.matirx=new int[total][total];
			this.paths=list;
		}
		
		
		//打印栈
		private  void printStack(Stack<Integer> stack,int k){
			for(Integer i:stack)
				System.out.print(i+"->");
			System.out.print(k);
			System.out.println();
	//		System.out.print(k);
		}
		
				//打印路径数组
				private void printPaths(ArrayList<Stack<Integer>> paths){
					for(int i=0;i<paths.size();i++){
						printStack(paths.get(i),this.total);
					}
					
				}
				
		
		private int countWeight(Stack<Integer> stack,int k){
			Stack<Integer> stack1=(Stack<Integer>) stack.clone();
		stack1.push(k);
			int s=0;
		while(stack1.size()!=1){
			 s=s+this.matirx[stack1.pop()][stack1.peek()];
			}			
		return s ;
		}
		
		//判断两点是否相连
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
		
		
		//向Path中添加path
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
				
			for(int i=0;i<this.total;i++){
				
				if(this.matirx[k][i]>=1&&k!=i){
					
					if(stack.contains(i)){
						
						continue;
					}
					
					if(i==goal){
					        Path path=new Path(stack, countWeight(stack,i));
						    addPaths(path);
						continue;
					}		
					
					stack.push(i);
					dfsStack(k, goal, stack);		
					
				}	
			}		
			
			stack.pop();	
				
		}
		
		//寻找路径集合中的最小路径
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
			
			System.out.println("---最短路径---");
			for(int i=0;i<n;i++){
				if(paths.get(i).weight==mincost){
					minPaths.add(paths.get(i));
					System.out.print("路径：");
				this.printStack(paths.get(i).path,this.total);
				System.out.print("经过点数："+(paths.get(i).path.size()+1)+" ");
			    System.out.print("路径长度:"+countWeight(paths.get(i).path,end)+"  ");
			    System.out.println();
               
                }
			}
			
			return minPaths;
		}
		
			
		//有环判定函数
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
					
					return -1;
				}
		
		//搜索符合所有条件路径中的有环和无环解
		private void searchPaths(ArrayList<Path> paths,int[][] graph,int n1,int n2,int b1,int b2,int b3,int b4){
			
			if(paths==null)
				return ;
			
			
			int min=Integer.MAX_VALUE;
			Path minpath=new Path(null, 0);
			for(int i=0;i<paths.size();i++){		
				if(paths.get(i).path.contains(n1)&&paths.get(i).path.contains(n2)&&isConnect(paths.get(i).path, b1, b2)&&isConnect(paths.get(i).path, b3, b4)&&paths.get(i).path.size()<9){	
					if(paths.get(i).weight<min){
						min=paths.get(i).weight;
						minpath=paths.get(i);
					}
								
				}		

				}
			
			if(minpath.path==null)
				System.out.println("\n-----无满足所有约束的最优解-----\n");
			
			
			//无环所有路径
			int minuncycle=Integer.MAX_VALUE;
			Path minpathuncycle=new Path(null, 0);
			for(int i=0;i<paths.size();i++){		
				if(paths.get(i).path.contains(n1)&&paths.get(i).path.contains(n2)&&isConnect(paths.get(i).path, b1, b2)&&isConnect(paths.get(i).path, b3, b4)){	
					if(paths.get(i).weight<minuncycle){
						minuncycle=paths.get(i).weight;
						minpathuncycle=paths.get(i);
					}
								
				}		

				}
//			System.out.println("---符合所有约束(无环最小)---");
//			System.out.print("路径：");
//			printStack(minpathuncycle.path,this.total-1);
//			System.out.print("经过点数："+(minpathuncycle.path.size()+1)+" ");
//			System.out.print("路径长度："+minpathuncycle.weight);
//			System.out.println();
			
			
			//有环路径
			int mincycle=Integer.MAX_VALUE;
			Path minpathcycle=new Path(null, 0);
				int[] num={n1,n2,b1,b2,b3,b4};
				int c1,c2;
				
				for(int i=0;i<paths.size();i++){

					for(int k=0;k<6;k++)
						for(int j=k+1;j<6;j++){
						c1=num[k];
						c2=num[j];
						if(isCycle(graph, c1, c2, b1, b2, b3, b4)!=-1){
							int flag=isCycle(graph, c1, c2, b1, b2, b3, b4);
							if(isConnect(paths.get(i).path, flag, c1)&&!paths.get(i).path.contains(c2)){
								paths.get(i).path=addNumInStack(paths.get(i).path, c2, flag);
								paths.get(i).weight=countWeight(paths.get(i).path, 17);
								if(paths.get(i).path.contains(n1)&&paths.get(i).path.contains(n2)&&isConnect(paths.get(i).path, b1, b2)&&isConnect(paths.get(i).path, b3, b4)){
									
									if(paths.get(i).weight<mincycle){
										mincycle=paths.get(i).weight;
										minpathcycle=paths.get(i);
									}
									
//									System.out.println("---符合所有约束(有环最小)---");
//									System.out.print("路径：");
//									printStack(paths.get(i).path,this.total);
//									System.out.print("路径长度："+paths.get(i).weight);
//									System.out.println();
									
								}
								break;
								
							}
							if(isConnect(paths.get(i).path, flag, c2)&&!paths.get(i).path.contains(c1)){
								paths.get(i).path=addNumInStack(paths.get(i).path, c1, flag);
								paths.get(i).weight=countWeight(paths.get(i).path, 17);
								if(paths.get(i).path.contains(n1)&&paths.get(i).path.contains(n2)&&isConnect(paths.get(i).path, b1, b2)&&isConnect(paths.get(i).path, b3, b4)){
									if(paths.get(i).weight<mincycle){
										mincycle=paths.get(i).weight;
										minpathcycle=paths.get(i);
									}
									
//									System.out.println("---符合所有约束(有环最小)---");
//									System.out.print("路径：");
//									printStack(paths.get(i).path,this,total);
//									System.out.print("路径长度："+paths.get(i).weight);
//									System.out.println();
									
								}
							}
							break;
						}
						}
				}
										
//					System.out.println("---符合所有约束(有环最小)---");
//					System.out.print("路径：");
//					printStack(minpathcycle.path,this.total-1);
//					System.out.print("经过点数："+(minpathcycle.path.size()+1)+" ");
//					System.out.print("路径长度："+minpathcycle.weight);
//					System.out.println();
					
				   System.out.println("-----次优解-----");
				   System.out.println("不满足经过点数小于9，满足其他约束");				   
					if(minpathuncycle.weight<minpathcycle.weight){
						
						System.out.print("路径：");
						printStack(minpathuncycle.path,this.total-1);
						System.out.print("经过点数："+(minpathuncycle.path.size()+1)+" ");
						System.out.print("路径长度："+minpathuncycle.weight);
						System.out.println();
					}
					else{
						System.out.print("路径：");
						printStack(minpathcycle.path,this.total-1);
						System.out.print("经过点数："+(minpathcycle.path.size()+1)+" ");
						System.out.print("路径长度："+minpathcycle.weight);
						System.out.println();
						
						
					}
					
					
		
		}
		
		private Stack<Integer> addNumInStack(Stack<Integer> stack,int c2,int flag){
			Stack<Integer> newstack=(Stack<Integer>)stack.clone();
			Stack<Integer> temp=new Stack<Integer>();
			while(newstack.peek()!=flag){
				temp.push(newstack.pop());
			}
			newstack.push(c2);
			newstack.push(flag);
			while(!temp.isEmpty()){
				newstack.push(temp.pop());
			}
			
			return newstack;
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
			return d[end];
		}
		
		
		//初始化邻接矩阵
		private void initGrf(int[][] graph){	
			
			
			int pointnum=graph[0][0];
			for(int i=0;i<pointnum;i++)
				for(int j=0;j<pointnum;j++)
					this.matirx[i][j]=0;
			
			int edgenum=graph[0][1];
			for(int i=1;i<=edgenum;i++)
				this.matirx[graph[i][1]][graph[i][2]]=this.matirx[graph[i][2]][graph[i][1]]=graph[i][3];	
			
			this.matirx[graph[0][8]][graph[0][9]]=this.matirx[graph[0][9]][graph[0][8]]=0;
			}
		
		//字符串转化为数组
		public static int[] converStringToInt(String str){
			if(str==null)
				return null;
			
			int[] s=new int[10];
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
			
			//读取文件
			File csv = new File("C://Users//shen//Desktop//中兴//topo2.csv");  // CSV文件路径
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
		    
		    int obj[][] = new int[5000][]; //文件中的值转化的矩阵
		    
		    try {
		            List<String> allString = new ArrayList<>();
		            while ((line = br.readLine()) != null)  //读取到的内容给line变量
		            {
		                everyLine = line;
		                //System.out.println(everyLine);
		                allString.add(everyLine);
		            }
		            int size = allString.size(); 
		            if (size == 0) { 
		                //log.info("集合为空,转换数组失败，将返回null！"); 
		               // return null; 
		            	System.out.println("集合为空");
		            } 
		            for (int i = 0; i < size; i++) { 
		                obj[i] =converStringToInt(allString.get(i)); 
		            } 	            
		    } catch (IOException e)
		    {
		        e.printStackTrace();
		    }
		   
		    
		    
		    ArrayList<Path> paths=new ArrayList<Path>();	//存储所有起点到终点（无重复点）的路径
//		    ArrayList<Path> minpaths=new ArrayList<Path>();//存储最短路径
		    int origin=0;
			int goal=obj[0][0];
		    GrfAllEdge grf=new GrfAllEdge(goal, paths);
			
			grf.initGrf(obj);

			System.out.print("-----寻找起点到终点的所有路径开始-----\n");
			
			Stack<Integer> stack=new Stack<Integer>();		
			stack.push(origin);
			//DFS搜索并保存所有路径
			grf.dfsStack(-1, goal-1, stack);
//			System.out.println();
//			System.out.println("路径总数："+paths.size());
			//输出最小路径
			//minpaths=grf.findMinPath(paths,goal-1);

			//在所有路径中搜索并输出满足约束的路径
			grf.searchPaths(paths,grf.matirx,obj[0][2],obj[0][3],obj[0][4],obj[0][5],obj[0][6],obj[0][7]);
			
			
			long endTime=System.currentTimeMillis();
			System.out.println();
			System.out.print("程序运行时间："+(endTime-startTime)+"ms");
			
			
			
		
		}
	
}
