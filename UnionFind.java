public class UnionFind {

	//The number of elements in this union find
	private int size;

	/*Used to track the size of each of the component i.e the number of elements in 
	each component*/ 
	private int[] sz;

	//id[i] points to the parent of i, if id[i] = i then i is a root node
	private int[] id;

	//Tracks the number of components in the union find
	private int numComponents;

	public UnionFind(int size) {

		if(size <= 0) throw new IllegalArgumentException("Size <= 0 is not allowed");

		this.size = numComponents = size;

		/*an int array to indicate size of each component
		points to each element since at creation, each element is a component
		of its own*/
		sz = new int[size];
		/*int array to point to the root node of each component. At creation, each
		element is a component of its own thus is its own root node*/

		for(int i = 0; i < size; i++) {
			/*Kind of creating a bijection here, get the elements and number them
			[0, n) where n is the number of elements*/
			id[i] = i;//Link to itself(self root)
			sz[i] = 1;//Each component is originally of size one
		}

	}

	public int find(int p) {

		//Find the root of the component/set
		int root = p;
		/*Below loop will loop backward till a node linking to itself is found
		(root node). Component identified by the root node*/
		while(root != id[root]) root = id[root];

		/*Having found the root node, compress the path leading to the root using an
		operation called "Path compression". This is what gives us amortized time
		complexity. Amortized time complexity is close to constant time complexity
		but not exactly constant time complexity*/
		while (p != root) {
			//p is the node whose path we compressing
			//root is the root node
			int next = id[p]; //variable "next" holds the node that is parent to p.
			id[p] = root; //The actual path compression for node p done here
			p = next; /*The next node whose path we compressing is that which was
						parent to p*/
		}

		return root;
	}

	/*Return whether or not the elements 'p' and 'q' are in the same components/set*/
	public boolean connected(int p, int q) {
		return find(p) == find(q);
	}

	//Return the size of the components/set 'p' belongs to
	public int componentSize(int p) {
		return sz[find(p)];
	}

	//Return the number of elements in this UnionFind/Disjoint set
	public int size() {
		return size;
	}

	//Returns the number of remaining components/sets
	public int components() {
		return numComponents;
	}

	//Unify the components/sets containing elements 'p' and 'q'
	public void unify(int p, int q) {

		int root1 = find(p);
		int root2 = find(q);

		//These elements are already in the same group!
		if(root1 == root2) return;

		//Merge smaller component/set into the larger one.
		if(sz[root1] < sz[root2]) {
			sz[root2] += sz[root1];
			id[root1] = root2;
		} else {
			sz[root1] += sz[root2];
			id[root2] = root1;
		}

		/*Since the roots found are differenr we know that the number of components
		/sets has decreased by one*/
		numComponents--;
	}
	
}