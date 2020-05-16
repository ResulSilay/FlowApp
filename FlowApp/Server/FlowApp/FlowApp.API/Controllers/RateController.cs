using AutoMapper;
using FlowApp.API.Helper;
using FlowApp.Business.Abstract;
using FlowApp.Core.DTOs;
using FlowApp.Entity.Models;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using System.Diagnostics;
using System.Net;
using System.Security.Claims;
using System.Threading.Tasks;

namespace App.API.Controllers
{
    [Produces("application/json")]
    [Route("api/rates")]
    [Authorize]
    [ApiController]

    public class RateController : Controller
    {
        private readonly IRateService _rateService;
        private readonly IPostService _postService;
        private readonly IUserService _userService;
        private readonly IMapper _mapper;

        public RateController(IRateService rateService, IPostService postService, IUserService userService, IMapper mapper)
        {
            _rateService = rateService;
            _postService = postService;
            _userService = userService;
            _mapper = mapper;
        }

        [HttpGet("{postId}")]
        public async Task<IActionResult> GetRateAsync(int postId)
        {
            var userId = int.Parse(User.FindFirst(ClaimTypes.NameIdentifier).Value);

            if (!await _postService.Any(postId))
                return NotFound();

            var rate = _rateService.GetRate(postId, userId);

            if (rate == null)
                return new ObjectActionResult(success: true, statusCode: HttpStatusCode.NotFound, data: null);

            return new ObjectActionResult(success: true, statusCode: HttpStatusCode.OK, data: rate);
        }

        [HttpPost()]
        public async Task<IActionResult> Rate(RateDto rateDto)
        {
            var userId = int.Parse(User.FindFirst(ClaimTypes.NameIdentifier).Value);

            if (!(await _postService.Any(rateDto.PostId)))
                return new ObjectActionResult(success: false, statusCode: HttpStatusCode.NotFound, data: null);

            if (await _rateService.Any(rateDto.PostId, userId))
            {
                var _rating = _rateService.GetRate(rateDto.PostId, userId);
                var rating = _mapper.Map<Rating>(rateDto);
                rating.Id = _rating.Id;
                rating.UserId = userId;
                var rate = _rateService.Update(rating);
                return new ObjectActionResult(success: true, statusCode: HttpStatusCode.OK, data: rate);
            }
            else
            {
                var rating = _mapper.Map<Rating>(rateDto);
                rating.UserId = userId;
                var rate = _rateService.Save(rating);
                return new ObjectActionResult(success: true, statusCode: HttpStatusCode.Created, data: rate);
            }
        }
    }
}