/**
 * 
 */
package com.flatironschool.javacs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Provides sorting algorithms.
 *
 */
public class ListSorter<T> {

	private T[] aux;

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void insertionSort(List<T> list, Comparator<T> comparator) {
	
		for (int i=1; i < list.size(); i++) {
			T elt_i = list.get(i);
			int j = i;
			while (j > 0) {
				T elt_j = list.get(j-1);
				if (comparator.compare(elt_i, elt_j) >= 0) {
					break;
				}
				list.set(j, elt_j);
				j--;
			}
			list.set(j, elt_i);
		}
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void mergeSortInPlace(List<T> list, Comparator<T> comparator) {
		List<T> sorted = mergeSort(list, comparator);
		list.clear();
		list.addAll(sorted);
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * Returns a list that might be new.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public List<T> mergeSort(List<T> list, Comparator<T> comparator) {
        List<T> sortList=new ArrayList<T>();
        sortList.addAll(list);
        aux=(T[])new Object[sortList.size()];
        Sort(sortList,0,sortList.size()-1,comparator);

        return sortList;
	}

	private void Sort(List<T> a,int lo,int hi,Comparator<T> comparator){
		if(lo>=hi) return;
		int mid=(lo+hi)/2;
		Sort(a,lo,mid,comparator);
		Sort(a,mid+1,hi,comparator);
		merge(a,lo,mid,hi,comparator);
	}

	private void merge(List<T> a,int lo,int mid,int hi,Comparator<T> comparator){
		for(int i=lo;i<=hi;i++){
			aux[i]=a.get(i);
		}
		int j=lo;
		int k=mid+1;
		for(int i=lo;i<=hi;i++){
			if(j>mid) a.set(i, aux[k++]);
			else if(k>hi) a.set(i, aux[j++]);
			else if (comparator.compare(aux[j],aux[k])<=0) {
				a.set(i,aux[j++]);
			}
			else
				a.set(i , aux[k++]);
		}
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void heapSort(List<T> list, Comparator<T> comparator) {
        Queue<T> heap=new PriorityQueue<T>(comparator);
        for(T token:list){
        	heap.offer(token);
        }
        list.clear();
        while(!heap.isEmpty()){
        	list.add(heap.poll());
        }
	}

	
	/**
	 * Returns the largest `k` elements in `list` in ascending order.
	 * 
	 * @param k
	 * @param list
	 * @param comparator
	 * @return 
	 * @return
	 */
	public List<T> topK(int k, List<T> list, Comparator<T> comparator) {
		Queue<T> heap=new PriorityQueue<T>(comparator);
        for(T token:list){
        	if(heap.size()<k)
        		heap.offer(token);
        	else{
        		T smallest=heap.peek();
        		if(comparator.compare(token, smallest)<=0) continue;
        		else{
        			heap.poll();
        			heap.offer(token);
        		}
        	}
        }
        List<T> newList=new ArrayList<>();
        while(!heap.isEmpty()){
        	newList.add(heap.poll());
        }
        return newList;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		
		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer n, Integer m) {
				return n.compareTo(m);
			}
		};
		
		ListSorter<Integer> sorter = new ListSorter<Integer>();
		sorter.insertionSort(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.mergeSortInPlace(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.heapSort(list, comparator);
		System.out.println(list);
	
		list = new ArrayList<Integer>(Arrays.asList(6, 3, 5, 8, 1, 4, 2, 7));
		List<Integer> queue = sorter.topK(4, list, comparator);
		System.out.println(queue);
	}
}
