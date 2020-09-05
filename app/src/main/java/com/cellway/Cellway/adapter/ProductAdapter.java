package com.cellway.Cellway.adapter;

//public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{
//
//    Context context;
//    ArrayList<Product_Description> arrProduct;
//
//    public ProductAdapter(Context context, ArrayList<Product_Description> arrProduct) {
//        this.context = context;
//        this.arrProduct = arrProduct;
//    }
//
//
//    @NonNull
//    @Override
//    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view;
//        LayoutInflater layoutInflater = LayoutInflater.from(context);
//        view = layoutInflater.inflate(R.layout.row_product,parent,false);
//        return new ProductViewHolder(view);
//    }
//
//
//    @Override
//    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
//        Product_Description obj = arrProduct.get(position);
//        Picasso.get().load(arrProduct.get(position).getImg_mobile()).into(holder.img_mobile);
//        holder.deduct_price.setText(obj.getDeduct_price());
//        holder.tv_brand.setText(obj.getBrand());
//        holder.tv_model.setText(obj.getModel());
//        holder.wrntyInOut.setText(obj.getTv_wrnty());
//        holder.tv_size.setText(obj.getTv_size());
//        holder.tv_gb.setText(obj.getGb());
//        holder.tv_battery.setText(obj.getTv_battery());
//        holder.tv_rear_amera.setText(obj.getTv_rear_amera());
//        holder.tv_front_amera.setText(obj.getTv_front_amera());
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return arrProduct.size();
//    }
//
//    public class ProductViewHolder extends RecyclerView.ViewHolder{
//        ImageView img_mobile;
//        TextView tv_brand,tv_model,tv_gb, deduct_price,
//                tv_size,tv_battery,tv_rear_amera,tv_front_amera,wrntyInOut;
//        public ProductViewHolder(@NonNull View itemView) {
//            super(itemView);
//            img_mobile = itemView.findViewById(R.id.img_mobile);
//            deduct_price = itemView.findViewById(R.id.deduct_price);
//            tv_brand = itemView.findViewById(R.id.tv_brand);
//            tv_model = itemView.findViewById(R.id.tv_model);
//            tv_gb = itemView.findViewById(R.id.tv_gb);
//            tv_size = itemView.findViewById(R.id.tv_size);
//            tv_battery = itemView.findViewById(R.id.tv_battery);
//            tv_rear_amera = itemView.findViewById(R.id.tv_rear_amera);
//            tv_front_amera = itemView.findViewById(R.id.tv_front_amera);
//            wrntyInOut = itemView.findViewById(R.id.wrntyInOut);
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    FragmentManager fragmentManager = ((Home) context).getSupportFragmentManager();
//                    Book frag = new Book();
//                    fragmentManager.beginTransaction().replace(R.id.frame,frag,frag.getTag())
//                    .addToBackStack(null).commit();
//
//                }
//            });
//
//        }
//    }
//}
