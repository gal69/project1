	public static int digitFeature1(int[][] digit, int index) {
		index*=28;
		int count=0;
		int limit=28;
		if(digit.length-index<28) {
			limit=digit.length-index;
		}
    
    //change under here
    
		for(int i=index;i<index+limit;i++) {
			for(int j=0;j<28;j++) {
				if(digit[i][j]==1) {
					count++;
				}
			}
		}

		if(count<125) {
			return 1;
		}
		return 0;
	}
