package test;

import java.util.Stack;

public class dijkstra {
	
	
	
	//dijkstra实现，输入为图的邻接矩阵和源点
	public static int[] dijkstraown(int[][] graph,int start){
		if(graph==null)
			return null;
		
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
	   printArr(p);
		return d;
	}
	
	
	
	
	
	
	
	public static void printArr(int[] arr){
		for(int i=0;i<arr.length;i++)
			System.out.print(arr[i]+" ");
		
		System.out.println();
		
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
	
	
	public static void main(String[] args){
		int[][] graph={{0,6,3,0,0,0},{6,0,2,5,0,0},{3,2,0,3,4,0},{0,5,3,0,2,3},{0,0,4,2,0,5},{0,0,0,3,5,0}};
		//char[] mVexs={'A','B','C','D','E','F'};
		replaceZeroToMax(graph);
		
		
		
		int[] d=dijkstraown(graph,0);
		
		
		printArr(d);
		

		
		
	}
	
	

}
