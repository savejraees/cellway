package com.cellway.Cellway.adapter;

public class BookAdapter{ /*extends RecyclerView.Adapter<BookAdapter.BookViewHolder>{

    Context context;
    ArrayList<BookModel> arrBook;


    public BookAdapter(Context context, ArrayList<BookModel> arrBook) {
        this.context = context;
        this.arrBook = arrBook;
    }


    @NonNull
    @Override
    public BookAdapter.BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.row_book,parent,false);
        return new BookAdapter.BookViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull BookAdapter.BookViewHolder holder, int position) {
        BookModel obj = arrBook.get(position);
        holder.tv_nm_clr_str.setText(obj.getTv_name_clr_strg());
        holder.tv_star.setText(obj.getTv_star());
        holder.tv_rating.setText(obj.getRating());
        holder.deduct_price.setText(obj.getDeduct_price());
        holder.tv_original_Price.setText(obj.getTv_original_Price());
        holder.tv_original_Price.setPaintFlags(holder.tv_original_Price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tv_off.setText(obj.getTv_off());


    }

    @Override
    public int getItemCount() {
        return arrBook.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder{

        TextView tv_nm_clr_str,tv_star,tv_rating,deduct_price,tv_original_Price,
                tv_off,discount;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_nm_clr_str = itemView.findViewById(R.id.tv_nm_clr_str);
            tv_star = itemView.findViewById(R.id.tv_star);
            tv_rating = itemView.findViewById(R.id.tv_rating);
            deduct_price = itemView.findViewById(R.id.deduct_price);
            tv_original_Price = itemView.findViewById(R.id.tv_original_Price);
            tv_off = itemView.findViewById(R.id.tv_off);
            discount = itemView.findViewById(R.id.discount);


        }
    }*/
}
