using FlowApp.API.Helper;
using FlowApp.Business.Abstract;
using AutoMapper;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using System.Net;
using System.Security.Claims;
using FlowApp.Entity.Models;
using FlowApp.Entity.Dtos;
using FlowApp.Core.DTOs;
using FlowApp.Core.Util.Hash;
using System.Threading.Tasks;

namespace FlowApp.API.Controllers
{
    [Produces("application/json")]
    [Route("api/user")]
    [Authorize]
    [ApiController]

    public class UserController : Controller
    {
        private readonly IUserService _userService;
        private readonly IMapper _mapper;

        public UserController(IUserService userService, IMapper mapper)
        {
            _userService = userService;
            _mapper = mapper;
        }

        [HttpGet]
        public IActionResult Get()
        {
            var userId = int.Parse(User.FindFirst(ClaimTypes.NameIdentifier).Value);
            var user = _userService.GetUser(userId);

            if (user == null)
                return new ObjectActionResult(success: false, statusCode: HttpStatusCode.BadRequest, data: null);

            var profileDto = _mapper.Map<ProfileDto>(user);

            return new ObjectActionResult(success: true, statusCode: HttpStatusCode.OK, data: profileDto);
        }

        [HttpPut]
        public IActionResult Save(ProfileEditDto profileEditDto)
        {
            var userId = int.Parse(User.FindFirst(ClaimTypes.NameIdentifier).Value);
            var user = _userService.GetUser(userId);

            if (user == null)
                return new ObjectActionResult(success: false, statusCode: HttpStatusCode.BadRequest, data: null);

            var userProfile = _mapper.Map<User>(profileEditDto);

            userProfile.Id = userId;
            userProfile.Email = user.Email;
            userProfile.Img = user.Img;
            userProfile.Password = user.Password;
            userProfile = _userService.Save(userProfile);
            var profileDto = _mapper.Map<ProfileDto>(userProfile);

            return new ObjectActionResult(success: true, statusCode: HttpStatusCode.OK, data: profileDto);
        }

        [HttpPost]
        public IActionResult ChangePassword(ProfilePasswordDto profilePasswordDto)
        {
            var userId = int.Parse(User.FindFirst(ClaimTypes.NameIdentifier).Value);
            var user = _userService.GetUser(userId);

            if (user == null && profilePasswordDto.Password == null)
                return new ObjectActionResult(success: false, statusCode: HttpStatusCode.BadRequest, data: null);

            user.Id = userId;
            user.Email = user.Email;
            user.Password = HashMD5.Create(profilePasswordDto.Password);
            _ = _userService.Save(user);

            return new ObjectActionResult(success: true, statusCode: HttpStatusCode.OK, data: null);
        }

        [HttpPost("changePicture")]
        public async Task<IActionResult> ChangePictureAsync(ProfileImgDto profileImgDto)
        {
            var userId = int.Parse(User.FindFirst(ClaimTypes.NameIdentifier).Value);
            var user = _userService.GetUser(userId);

            if (user == null && profileImgDto.Img == null)
                return new ObjectActionResult(success: false, statusCode: HttpStatusCode.BadRequest, data: null);

            user.Id = userId;
            user.Email = user.Email;
            user.Password = user.Password;
            user.Img = profileImgDto.Img;
            var _user = await _userService.SaveImageAsync(user);
            var profileDto = _mapper.Map<ProfileDto>(_user);

            return new ObjectActionResult(success: true, statusCode: HttpStatusCode.OK, data: profileDto);
        }
    }
}