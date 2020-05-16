using AutoMapper;
using FlowApp.Entity.Models;
using FlowApp.Core.DTOs;
using FlowApp.Entity.Dtos;

namespace FlowApp.API.Helper
{
    public class AutoMapperProfiles : Profile
    {
        public AutoMapperProfiles()
        {
            CreateMap<UserLoginDto, User>();
            CreateMap<UserRegisterDto, User>();
            CreateMap<ProfileDto, User>();
            CreateMap<User, ProfileDto>();
            CreateMap<Post, PostDto>();
            CreateMap<PostDto, Post>();
            CreateMap<Rating, RateDto>();
            CreateMap<RateDto, Rating>();
            CreateMap<User, ProfileEditDto>();
            CreateMap<ProfileEditDto, User>();
        }
    }
}