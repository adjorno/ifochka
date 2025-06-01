config.output = {
  ...config.output,
  publicPath: "/blog/",
};

config.devServer = {
  ...config.devServer,
  historyApiFallback: true,
};