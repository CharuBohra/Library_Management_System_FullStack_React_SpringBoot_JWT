const FineStateCard = ({
  label,
  value,
  hint,
  valueClass = "text-gray-700",
}) => {
  return (
    <div className="flex items-start gap-3 justify-between border border-gray-200 rounded-xl shadow-sm space-y-2 p-5 bg-white">
      <div className="flex flex-col flex-1 gap-2">
        <span className=" text-sm text-gray-500 uppercase tracking-tight">
          {label}
        </span>
        <span className={`text-4xl font-extrabold ${valueClass}`}>{value}</span>
        {hint && <span className="text-sm text-gray-400">{hint}</span>}
      </div>
    </div>
  );
};

export default FineStateCard;
