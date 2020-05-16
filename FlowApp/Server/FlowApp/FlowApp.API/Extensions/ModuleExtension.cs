using FlowApp.Data.Repository.EF;
using AutoMapper;
using FlowApp.Data.Abstract;
using Microsoft.Extensions.DependencyInjection;
using FlowApp.Business.Abstract;
using FlowApp.Business.Services;
using FlowApp.Core.Util.Azure;

namespace FlowApp.API.Extensions
{
    public static class ModuleExtension
    {
        public static void AddScopedServices(this IServiceCollection services)
        {
            services.AddSingleton<IMapper, Mapper>();

            services.AddScoped<IAuthService, AuthService>();
            services.AddScoped<IUserService, UserService>();
            services.AddScoped<IPostService, PostService>();
            services.AddScoped<IRateService, RateService>();

            services.AddScoped<IAuthRepository, EfAuthRepository>();
            services.AddScoped<IUserRepository, EfUserRepository>();
            services.AddScoped<IPostRepository, EfPostRepository>();
            services.AddScoped<IRateRepository, EfRateRepository>();
            services.AddScoped<ITextRepository, EfTextRepository>();
            services.AddScoped<IPictureRepository, EfPictureRepository>();

            services.AddScoped<IAzureStorageService, AzureStorageService>();
        }
    }
}
