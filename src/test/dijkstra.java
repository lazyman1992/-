package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class dijkstra {
	
	
	
	//记录最短路径和路径长度
	public class Path{
		int id;
		Stack<Integer> path;
		int cost;
		
		public Path(int id,Stack<Integer> stack,int cost){
			this.id=id;
			this.path=(Stack<Integer>)stack.clone();
			this.cost=cost;
		}
	}
	
	ArrayList<Path> paths=new ArrayList<Path>();
	
	
	//dijkstra实现，输入为图的邻接矩阵和源点
	public static int dijkstraown(int[][] graph,int start,int end){
		if(graph==null)
			return 0;
		
		
		replaceZeroToMax(graph);
		
		//初始化d p向量
		int n=graph[0].length;
		int[] d=new int[n];//最短路径
		int[] p=new int[n];//最短路径前驱节点
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
		printArr(d);
	   printArr(p);
	   //Stack<Integer> stack=new Stack<Integer>();
	 //  findpath(p, end, start, stack);
	  // Path path=new Path(end, stack, d[end]);
		return d[end];
		
		
	}
	
	public static void printStack1(Stack<Integer> stack){
		for(Integer i:stack)
			System.out.print(i+" ");
		//System.out.println();
	}
	
	//寻找路径
//	public static void findpath(int[] p,int end,int start,Stack<Integer> stack){
//	
//		stack.push(end);
//		while(stack.peek()!=start){
//			findpath(p, p[end], start,stack);
//		}
//		
//	
//	}
	
	//判断有环
	public static int connect(int[][] graph,int n1,int n2,int b1,int b2,int b3,int b4){
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
	
	
	//floyd算法求两点最小路径
	public static int[][] floyd(int[][] graph){
		int n=graph.length;
		for(int k=0;k<n;k++)
			for(int i=0;i<n;i++)
				for(int j=0;j<n;j++){
					if(graph[i][j]>graph[i][k]+graph[k][j]&&(graph[i][j]!=0&&graph[i][k]!=0&&graph[k][j]!=0))
						graph[i][j]=graph[i][k]+graph[k][j];
				}
		
		return graph;
	}
	
	
	
	public static void printArr(int[] arr){
		for(int i=0;i<arr.length;i++)
			System.out.print(arr[i]+" ");
		
		System.out.println();
		
	}
	
	
	public static void printMatrix(int[][] graph){
		int r=graph.length;
		int c=graph[0].length;
		for(int i=0;i<r;i++){
			for(int j=0;j<c;j++){
				System.out.print(graph[i][j]+" ");
			}
			System.out.println();
			}
	}
	
	
	public static void replaceZeroToMax(int[][] graph){
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
	
	public  static int[][] initGrf(int[][] graph){	
		
		
		int pointnum=graph[0][0];
		int[][] matirx=new int[pointnum][pointnum];
		System.out.println(pointnum);
		for(int i=0;i<pointnum;i++)
			for(int j=0;j<pointnum;j++)
				matirx[i][j]=0;
		
		int edgenum=graph[0][1];
		for(int i=1;i<=edgenum;i++)
			matirx[graph[i][1]][graph[i][2]]=matirx[graph[i][2]][graph[i][1]]=graph[i][3];		
		
		
		return matirx;
	}
	
	public static void main(String[] args){
		
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
	    int obj[][] = new int[5000][]; 
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
	            //System.out.println("csv表格中所有行数："+allString.size());
	    } catch (IOException e)
	    {
	        e.printStackTrace();
	    }

	    
	    int[][] graph=initGrf(obj);
		
	    System.out.println(connect(graph, 7, 12,2,4,13,14));
	    
	    
		
	}
	
	

	
	
	
	
}
