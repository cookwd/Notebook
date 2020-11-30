     	public void showdeleteDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		LayoutInflater inflater = getLayoutInflater();
	    View layout = inflater.inflate(R.layout.dialog, null);
	    builder.setView(layout);
	    builder.setIcon(R.drawable.ic_launcher);
		builder.setTitle(R.string.firstdialogtitle);
		builder.setMessage("请问您是否要删除所有记事？");
		builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub				
				dbAdapter.deleteAllData();
				Diaryadapter.clear(); //删除数据后清空listview显示
	            Toast.makeText(getApplication(), "日志已删除", Toast.LENGTH_SHORT).show();			             
			}
		});
		builder.setNegativeButton("cancel",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				});
		final AlertDialog dlg = builder.create();
		dlg.show();
	}

		Button editordialog  = (Button)layout.findViewById(R.id.editordialog);
		editordialog.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub								
				Intent intent = new Intent(Firstpage.this,WriteDiaryActivity.class);
				String spStr[] = itemdata1.split("日记内容:");//匹配字符串
				String spStr1[] = itemdata1.split(",时间");//匹配字符串
				intent.putExtra("diary", spStr[1]);  //获取内容
				intent.putExtra("timeid", spStr1[0]);//获取id
				//intent.putExtra("", "");
//				Bundle bundle=new Bundle();  
//	            bundle.putString("result", "第一个activity的内容");  
//	            bundle.putString("content",content);  
//	            intent.putExtras(bundle);  
				startActivity(intent);
				//finish();//跳转后结束dialog
			}
		});

	public class saveListener implements OnClickListener{
 
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub					
				SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd"); 				
		        data   =   sDateFormat.format(new  java.util.Date()); 
				//if(diary!=null)
	           // {
	                //return;            
		            People people = new People();
		            people.Time = data;
		            people.Diary = diarycontent.getText().toString();//获取输入日志内容		           
		            //people.Height = 1;
		            long colunm = dbAdapter.insert(people);
		            if(colunm == -1)
		            {
		                Toast.makeText(getApplication(), "添加错误", Toast.LENGTH_SHORT).show();
		            }
		            else
		            {
		            	Toast.makeText(getApplication(), "成功添加数据 ", Toast.LENGTH_SHORT).show();
		               // Toast.makeText(getApplication(), "成功添加数据 , ID: "+String.valueOf(colunm), Toast.LENGTH_SHORT).show();
		            }   
		          //  Firstpage.displaydiary1();//当点击保存后更新listview列表数据
		            finish();//写完日记后结束该activity以免多次保存
		}
		
	}
	
	/**
	 * 修改内容监听器
	 * @author xing
	 *
	 */
	public class changeListener implements OnClickListener{
 
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub			
		 
			People people = new People();
            people.Diary = diarycontent.getText().toString();
            people.Time  = data;
            //people.Height = Float.parseFloat(heightEdit.getText().toString());
            Intent intent=getIntent();  
            timeid=intent.getStringExtra("timeid");     
            if(timeid!=null){
            	 
            	  int  id = Integer.parseInt(timeid);
                 // Toast.makeText(getApplication(),timeid, Toast.LENGTH_SHORT).show();          
                  long count = dbAdapter.updateOneData(id, people);      
                   
                   if(count == -1 )
                   {
                   	Toast.makeText(getApplication(), "更新错误", Toast.LENGTH_SHORT).show();
                       //display.setText("");
                   }
                   else
                   {
                   	//Toast.makeText(getApplication(), "更新成功"+"更新数据第"+String.valueOf(id)+"条", Toast.LENGTH_SHORT).show();
                   	Toast.makeText(getApplication(), "更新成功", Toast.LENGTH_SHORT).show(); 
                   }
                   
                   finish(); 
            }else{
            	Toast.makeText(getApplication(), "请点击保存", Toast.LENGTH_SHORT).show();
            }                     
			//hintKb();		
		}

	public void showDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		LayoutInflater inflater = getLayoutInflater();
	    View layout = inflater.inflate(R.layout.dialog, null);
	    builder.setView(layout);
	    builder.setIcon(R.drawable.ic_launcher);
		builder.setTitle(R.string.dialogtitle);
		builder.setMessage("请问您是否要删除该天记事？");
		builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				
				diarycontent.setText("");
				Toast.makeText(getBaseContext(), "删除成功", Toast.LENGTH_SHORT).show();
			}
		});
		builder.setNegativeButton("cancel",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//finish();
					}
				});
		final AlertDialog dlg = builder.create();
		dlg.show();
	}

	public void time(){
		
		SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd hh:mm");     
        String   date   =   sDateFormat.format(new  java.util.Date());  
        time.setText(date);
	}
	/**
	 * 获取手机当前年月日
	 */
	public void time1(){
		SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd"); 				
        data   =   sDateFormat.format(new  java.util.Date()); 
	}

   protected void onCreate(Bundle savedInstanceState) {  
        // TODO Auto-generated method stub  
        super.onCreate(savedInstanceState);  
      //去除标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.firstpage);       
      //获得实例
        dbAdapter = new DBAdapter(this);
        //打开数据库
        dbAdapter.open();
        initUI();
       
    }
	/**
	 * 初始化UI
	 */
	private void initUI() {
		// TODO Auto-generated method stub
		 // 初使化设备存储数组
		Diaryadapter = new ArrayAdapter<String>(this, R.layout.listview_format);
		diarylistview = (ListView)findViewById(R.id.diaryshow);
		diarylistview.setOnItemClickListener(new diarylistviewItemListener());
		//Diaryadapter.clear();
		diarylistview.setAdapter(Diaryadapter);
		
		firstfinish = (Button)findViewById(R.id.firstfinish);
		firstfinish.setOnClickListener(new firstfinishListener());	
				
		set = (Button)findViewById(R.id.set);
		set.setOnClickListener(new setListener());		
		
		writediary = (Button)findViewById(R.id.writediary);
		writediary.setOnClickListener(new writeListener());
		
		deletediary = (Button)findViewById(R.id.deletediary);
		deletediary.setOnClickListener(new deletediaryListener());
		
		scandiary = (Button)findViewById(R.id.scandiary);
		scandiary.setOnClickListener(new scandiaryListener());
		displaydiary();//登录进去后显示所有日记
		photodialog();
//		handler.removeCallbacks(runnable);
//		handler.postDelayed(runnable,1000);
	}
	/**
	 * 由于更新完数据和写完日记后listview不能自动更新，如果在writeDiaryActivity调用display()
	 * 方法会出现空指针，因此添加一个定时器定时刷新listview
	 */
	private Handler handler = new Handler();
	private Runnable runnable = new Runnable() {
         public void run () {
        	 displaydiary();
         handler.postDelayed(this,1000); 
      }
    };
    
    /**
     * 退出
     * @author aeon
     *
     */
    public class firstfinishListener implements OnClickListener{
 
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			finish();
		}
    	
    }


public class DBAdapter
{
    public static final String DB_ACTION="db_action";//LogCat
    
    private static final String DB_NAME="people.db";//数据库名
    private static final String DB_TABLE="peopleinfo";//数据库表名
    private static final int    DB_VERSION=1;//数据库版本号
    
    public static final String KEY_ID = "_id";  //表属性ID
    public static final String KEY_NAME = "name";//表属性name
    public static final String KEY_AGE  = "age";//表属性age
    public static final String KEY_HEIGHT = "height";//表属性height
    
    private SQLiteDatabase db ;
    private Context xContext ;
    private DBOpenHelper dbOpenHelper ;
    public DBAdapter(Context context)
    {
        xContext = context ;
    }
    
    /** 
     * 空间不够存储的时候设为只读
     * @throws SQLiteException
     */
    public void open() throws SQLiteException
    {
        dbOpenHelper = new DBOpenHelper(xContext, DB_NAME, null,DB_VERSION);
        try
        {
            db = dbOpenHelper.getWritableDatabase();
        }
        catch (SQLiteException e)
        {
            db = dbOpenHelper.getReadableDatabase();
        }
    }
    
    public void close()
    {
        if(db != null)
        {
            db.close();
            db = null;
        }
    }
    /**
     * 向表中添加一条数据
     * @param people
     * @return
     */
    public long insert(People people)
    {
        ContentValues newValues = new ContentValues();
        
        newValues.put(KEY_NAME, people.Diary);
        newValues.put(KEY_AGE, people.Time);
       // newValues.put(KEY_HEIGHT, people.Height);
        
        return db.insert(DB_TABLE, null, newValues);
    }
    
    /**
     * 删除一条数据
     * @param id
     * @return
     */
    public long deleteOneData(long id)
    {
        return db.delete(DB_TABLE, KEY_ID+"="+id, null );
    }
    /**
     * 删除所有数据
     * @return
     */
    public long deleteAllData()
    {    	 
        return db.delete(DB_TABLE, null, null);
    }
    /**
     * 根据id查询数据的代码
     * @param id
     * @return
     */
    public People[] queryOneData(long id)
    {
        Cursor result = db.query(DB_TABLE, new String[] {KEY_ID,KEY_NAME,KEY_AGE,KEY_HEIGHT}, 
                KEY_ID+"="+id, null, null, null, null);
        return ConvertToPeople(result) ;
    }
    /**
     * 查询全部数据的代码
     * @return
     */
    public People[] queryAllData()
    {
        Cursor result = db.query(DB_TABLE, new String[] {KEY_ID,KEY_NAME,KEY_AGE,KEY_HEIGHT}, 
                null, null, null, null, null);
        return ConvertToPeople(result);
    }
    /**
     * 更新数据
     * @param id
     * @param people
     * @return
     */
    public long updateOneData(long id ,People people)
    {
        ContentValues newValues = new ContentValues();
        
        newValues.put(KEY_NAME, people.Diary);
        newValues.put(KEY_AGE, people.Time);
       // newValues.put(KEY_HEIGHT, people.Height);
        
        return db.update(DB_TABLE, newValues, KEY_ID+"="+id, null);
    }
    
    private People[] ConvertToPeople(Cursor cursor)
    {
        int resultCounts = cursor.getCount();
        if(resultCounts == 0 || !cursor.moveToFirst())
        {
            return null ;
        }
        People[] peoples = new People[resultCounts];
        Log.i(DB_ACTION, "PeoPle len:"+peoples.length);
        for (int i = 0; i < resultCounts; i++)
        {
            peoples[i] = new People();
            peoples[i].ID = cursor.getInt(0);
            peoples[i].Diary = cursor.getString(cursor.getColumnIndex(KEY_NAME));
            peoples[i].Time  = cursor.getString(cursor.getColumnIndex(KEY_AGE));
           // peoples[i].Height = cursor.getFloat(cursor.getColumnIndex(KEY_HEIGHT));
            Log.i(DB_ACTION, "people "+i+"info :"+peoples[i].toString());
            cursor.moveToNext();
        }
        return peoples;
    }
    
    
    /**
     * 静态Helper类，用于建立、更新和打开数据库
     */
    private static class DBOpenHelper extends SQLiteOpenHelper
    {
        /*
         * 手动建库代码
        CREATE TABLE peopleinfo
        (_id integer primary key autoincrement,
        name text not null,
        age integer,
        height float);*/
        private static final String DB_CREATE=
        "CREATE TABLE "+DB_TABLE
        +" ("+KEY_ID+" integer primary key autoincrement, "
        +KEY_NAME+" text not null, "
        +KEY_AGE+" integer,"+
        KEY_HEIGHT+" float);";
        public DBOpenHelper(Context context, String name,
                CursorFactory factory, int version)
        {
            super(context, name, factory, version);
        }
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(DB_CREATE);
            Log.i(DB_ACTION, "onCreate");
        }
        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion)
        {
            //函数在数据库需要升级时被调用，
            //一般用来删除旧的数据库表，
            //并将数据转移到新版本的数据库表中
            _db.execSQL("DROP TABLE IF EXISTS "+DB_TABLE);
            onCreate(_db);
            Log.i(DB_ACTION, "Upgrade");
        }
    }
}
