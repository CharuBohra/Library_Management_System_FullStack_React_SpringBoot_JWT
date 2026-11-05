const PageHeader = ({ title, subtitle, icon, action }) => (
  <div className="flex flex-wrap items-center justify-between gap-4 mb-8">
    <div>
      <h1 className="text-4xl font-bold text-gray-900 mb-2 flex items-center space-x-4">
        <span>{icon}</span>
        <span className="bg-linear-to-br from-indigo-600 to-purple-600 bg-clip-text text-transparent mb-1">
          {title}
        </span>
      </h1>
      {subtitle && <p className="text-lg text-gray-600">{subtitle}</p>}
    </div>
    {action}
  </div>
);

export default PageHeader;
